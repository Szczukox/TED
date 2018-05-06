import scala.collection.mutable.Map
class CSVProcessor(csvFile: String, isoMap: Map[String, Int], valueMap: Map[String, Double]) {
  def process() : (Map[String, Int], Map[String, Double]) = {
    import io.Source
    import java.util.Locale
    val source = Source.fromURL(csvFile)
    val head = source.getLines().next().split(",(?!(?=[^\"]*\"[^\"]*(?:\"[^\"]*\"[^\"]*)*$))")
    val isoID = head.indexOf("\"ISO_COUNTRY_CODE\"")
    val valueID = head.indexOf("\"VALUE_EURO_FIN_1\"")
    for (row <- source.getLines()) {
      val columns = row.split(",(?!(?=[^\"]*\"[^\"]*(?:\"[^\"]*\"[^\"]*)*$))")
      if (columns.length > valueID) {
        if (columns(valueID) != "") {
          val countryName = new Locale("", columns(isoID).substring(1, columns(isoID).length - 1)).getDisplayCountry
          if (isoMap.contains(countryName)) {
            isoMap(countryName) = isoMap(countryName) + 1
            valueMap(countryName) += columns(valueID).toDouble
          }
          else {
            isoMap += (countryName -> 1)
            valueMap += (countryName -> columns(valueID).toDouble)
          }
        }
      }
    }
    return (isoMap, valueMap)
  }
}
