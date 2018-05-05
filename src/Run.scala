object Run extends App {
  import scala.collection.mutable.Map
  import java.text.DecimalFormat
  val dateList = Array("TED_CAN_2006.csv", "TED_CAN_2007.csv")
  var contractSumAmount = Map[String, Int]()
  var contractSumValue = Map[String, Double]()
  for (file <- dateList) {
    val csvFile = new CSVProcessor(file, contractSumAmount, contractSumValue)
    val dataTuple = csvFile.process()
    contractSumAmount = dataTuple._1
    contractSumValue = dataTuple._2
  }
  for (key <- contractSumAmount.keys) {
    println("Średnia liczba zamówień dla kraju " + key + " wynosi: " + contractSumAmount(key) / dateList.length)
    val decimalFormat = new DecimalFormat("0,000.00")
    println("Średnia kwota zamówienia dla kraju " + key + " wynosi: " + decimalFormat.format(contractSumValue(key) / contractSumAmount(key)) + " EURO")
    println("")
  }
}
