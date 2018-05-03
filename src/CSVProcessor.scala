class CSVProcessor(csvFile: String) {
  import io.Source
  import scala.collection.mutable.Map
  val source = Source.fromURL(csvFile)
  val countryID = source.getLines().next().split(",(?!(?=[^\"]*\"[^\"]*(?:\"[^\"]*\"[^\"]*)*$))").map(_.trim).indexOf("\"ISO_COUNTRY_CODE\"")
  var countryIdMap = Map[String, Int]()
  for (row <- source.getLines()) {
    val columns = row.split(",(?!(?=[^\"]*\"[^\"]*(?:\"[^\"]*\"[^\"]*)*$))").map(_.trim)
    if (countryIdMap.contains(columns(countryID))) countryIdMap(columns(countryID)) = countryIdMap(columns(countryID)) + 1
    else countryIdMap += (columns(countryID) -> 1)
  }
  println(countryIdMap)
}
