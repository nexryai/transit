package me.nexryai.transit.services

import me.nexryai.transit.entities.Train
import me.nexryai.transit.entities.Transfer
import me.nexryai.transit.entities.TransitInfo
import me.nexryai.transit.entities.TransitParams
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class TransitInfoService(private val params: TransitParams) {
    private val userAgent: String = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:126.0) Gecko/20100101 Firefox/126.0"

    private fun genUrl(): String {
        if (params.from.isEmpty() || params.to.isEmpty()) {
            throw IllegalArgumentException("From or To is empty")
        }

        var url = "https://transit.yahoo.co.jp/search/print"
        url += "?from=${params.from}"
        url += "&to=${params.to}"
        return url
    }

    private fun getJsoupDocument(url: String): Document {
        if (url.isEmpty()) {
            throw IllegalArgumentException("URL is empty")
        }

        val con = Jsoup.connect(url)
        con.userAgent(userAgent)

        return con.get()
    }

    private fun analyzeDocument(routeDocument: Document): TransitInfo {
        // 経路のサマリーを取得
        val routeSummary = routeDocument.select("div.routeSummary")

        // 所要時間を取得
        val requiredTime = routeSummary.select("li.time").text()

        // 乗り換え回数を取得
        val transferCount = routeSummary.select("li.transfer").text()

        // 料金を取得
        val fare = routeSummary.select("li.fare").text()

        println("所要時間：$requiredTime")
        println(transferCount)
        println("料金：$fare")

        // 乗り換えの詳細情報を取得
        val routeDetail = routeDocument.select("div.routeDetail")

        // 乗換駅の取得
        var transferResults = mutableListOf<Transfer>()
        val stations = routeDetail.select("div.station")
        for (station in stations) {
            val stationName = station.select("dt").text()
            var ridingProps = station.select("p.ridingPos").text()
            if (ridingProps.isEmpty()) {
                ridingProps = "乗車位置に関する情報はありません"
            }

            val timeElms = station.select("ul.time").eachText()[0].split(" ")
            val arriveAt = timeElms[0].removeSuffix("着")
            // 出発駅 or 到着駅
            val departAt = try {
                timeElms[1].removeSuffix("発")
            } catch (e: IndexOutOfBoundsException) {
                arriveAt
            }

            println("到着:$arriveAt 出発:${departAt}; $stationName <$ridingProps>")
            transferResults.add(Transfer(stationName, arriveAt, departAt, ridingProps, null))
        }

        // 乗り換え路線の取得
        val trains = routeDetail.select("div.access")
        for ((i, train) in trains.withIndex()) {
            val trainElm = train.select("li.transport")
            val trainName = trainElm.select("div").first()?.ownText() ?: "不明"

            val destination = train.select("span.destination").text()
            val platform = train.select("span.platform").text()

            println(" | $trainName $destination [$platform]")

            val t = Train(trainName, destination, 0)
            transferResults[i].train = t
        }

        // validation
        if (stations.size != trains.size + 1) {
            throw IllegalArgumentException("Invalid data: stations.size != trains.size + 1")
        }

        val lines = routeDetail.select("li.transport").eachText()

        // 路線ごとの所要時間を取得
        val estimatedTimes = routeDetail.select("li.estimatedTime").eachText()

        // 路線ごとの料金を取得
        val fares = routeDetail.select("p.fare").eachText()

        // ToDo
        val result = TransitInfo(fare, 0, transferResults)
        return result
    }

    fun getTransit(): TransitInfo {
        val url = genUrl()
        val doc = getJsoupDocument(url)

        return analyzeDocument(doc)
    }
}