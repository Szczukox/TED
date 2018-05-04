class CSVProcessor(csvFile: String) {
  import io.Source
  import scala.collection.mutable.Map
  import java.util.Locale
  val source = Source.fromFile(csvFile)
  val head = source.getLines().next().split(",(?!(?=[^\"]*\"[^\"]*(?:\"[^\"]*\"[^\"]*)*$))")
  val isoID = head.indexOf("\"ISO_COUNTRY_CODE\"")
  val valueID = head.indexOf("\"VALUE_EURO_FIN_1\"")
  var isoMap = Map[String, Int]()
  var valueMap = Map[String, Double]()
  for (row <- source.getLines()) {
    val columns = row.split(",(?!(?=[^\"]*\"[^\"]*(?:\"[^\"]*\"[^\"]*)*$))")
    if (columns.length > valueID) {
      if (columns(valueID) != "") {
        val countryName = new Locale("", columns(isoID).substring(1, columns(isoID).length - 1)).getDisplayCountry
        if (isoMap.contains(countryName)) {
          isoMap(countryName) = isoMap(countryName) + 1
          valueMap(countryName) += columns(valueID).toDouble
        }
        else{
          isoMap += (countryName -> 1)
          valueMap += (countryName -> columns(valueID).toDouble)
        }
      }
    }
  }
  println(isoMap)
  println(isoMap.values.sum)
  println("")
  for (key <- isoMap.keys) {
    valueMap(key) /= isoMap(key)
  }
  println(valueMap)
}
