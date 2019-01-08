package com.wardziniak.kafka.scala.app.basic

import com.typesafe.scalalogging.LazyLogging
import com.wardziniak.kafka.utils._
import com.wardziniak.kafka.scala.config.ProducerConfigBuilder
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.util.Random

object BasicKafkaProducerScalaApp extends App with LazyLogging {


  case class ElasticsearchSimplifiedConfiguration(
    referenceFieldsUsedInElasticSearch: Seq[FieldConfiguration],
    synonyms: Option[SynonymsConfiguration] = None
  )

  case class FieldConfiguration(
    fieldName: String,
    caseInsensitiveSort: Option[Boolean] = None
  )

  case class SynonymsConfiguration(synonyms: Map[String, Seq[String]])
  case class BridgeCatalogConfigurationApi(
    id: Long,
    subConfiguration: String,
    countryName: String,
    dataOfferingName: String,
    dataOfferingStreamName: String,
    displayConfig: String,
    referencesDisplayConfig: String,
    referenceElasticConfig: ElasticsearchSimplifiedConfiguration,
    bridgeRecordElasticConfig: ElasticsearchSimplifiedConfiguration)

  val configApi = BridgeCatalogConfigurationApi(
    id = 1,
    subConfiguration = "multi",
    countryName = "Poland",
    dataOfferingName = "offerName",
    dataOfferingStreamName = "streamName",
    displayConfig = "{\n  \"columns\": [\n    {\n      \"headerName\":\"Raw Data\",\n      \"children\":[\n        {\n          \"headerName\":\"Score\",\n          \"field\":\"rawData.score\",\n          \"pinned\":\"left\",\n          \"width\":50,\n          \"sort\":\"desc\"\n        }\n      ]\n    },\n    {\n      \"headerName\": \"Raw Data\",\n      \"children\": [\n        {\n          \"headerName\": \"Opis\",\n          \"field\": \"rawData.productDescription\"\n        },\n        {\n          \"headerName\": \"EAN\",\n          \"field\": \"rawData.ean\"\n        },\n        {\n          \"headerName\": \"Producent\",\n          \"field\": \"rawData.manufacturer\"\n        },\n        {\n          \"headerName\": \"BAZYL\",\n          \"field\": \"rawData.bazyl\"\n        },\n        {\n          \"headerName\": \"Zrodlo\",\n          \"field\": \"rawData.sourceTypeWithFrequency\"\n        }\n      ]\n    },\n    {\n      \"headerName\": \"Reference\",\n      \"children\": [\n        {\n          \"headerName\": \"Opis APC\",\n          \"field\": \"referenceData.productDescription\"\n        },\n        {\n          \"headerName\": \"APC\",\n          \"field\": \"referenceData.productCodeBase\"\n        },\n        {\n          \"headerName\": \"Producent (APC)\",\n          \"field\": \"referenceData.manufacturer\"\n        }\n      ]\n    },\n    {\n      \"headerName\": \"Bridge metrics\",\n      \"children\": [\n        {\n          \"headerName\": \"Creation date\",\n          \"field\": \"rawData.bridgeTimestamp\",\n          \"width\": 140\n        },\n        {\n          \"headerName\": \"Source\",\n          \"field\": \"source\",\n          \"width\": 50\n        },\n        {\n          \"field\": \"rawData.bridgerLogin\",\n          \"width\": 120,\n          \"headerName\": \"Bridger\"\n        },\n        {\n          \"field\": \"rawData.checkerLogin\",\n          \"width\": 120,\n          \"headerName\": \"Checker\"\n        },\n        {\n          \"field\": \"rawData.factor\",\n          \"width\": 80,\n          \"headerName\": \"Quantity factor\"\n        }\n      ]\n    }\n  ]\n}",
    referenceElasticConfig = ElasticsearchSimplifiedConfiguration(referenceFieldsUsedInElasticSearch = Seq(FieldConfiguration("name1"), FieldConfiguration("name2"))),
    referencesDisplayConfig = "{\n  \"columns\": [\n    {\n      \"headerName\":\"Raw Data\",\n      \"children\":[\n        {\n          \"headerName\":\"Score\",\n          \"field\":\"rawData.score\",\n          \"pinned\":\"left\",\n          \"width\":50,\n          \"sort\":\"desc\"\n        }\n      ]\n    },\n    {\n      \"headerName\": \"Raw Data\",\n      \"children\": [\n        {\n          \"headerName\": \"Opis\",\n          \"field\": \"rawData.productDescription\"\n        },\n        {\n          \"headerName\": \"EAN\",\n          \"field\": \"rawData.ean\"\n        },\n        {\n          \"headerName\": \"Producent\",\n          \"field\": \"rawData.manufacturer\"\n        },\n        {\n          \"headerName\": \"BAZYL\",\n          \"field\": \"rawData.bazyl\"\n        },\n        {\n          \"headerName\": \"Zrodlo\",\n          \"field\": \"rawData.sourceTypeWithFrequency\"\n        }\n      ]\n    },\n    {\n      \"headerName\": \"Reference\",\n      \"children\": [\n        {\n          \"headerName\": \"Opis APC\",\n          \"field\": \"referenceData.productDescription\"\n        },\n        {\n          \"headerName\": \"APC\",\n          \"field\": \"referenceData.productCodeBase\"\n        },\n        {\n          \"headerName\": \"Producent (APC)\",\n          \"field\": \"referenceData.manufacturer\"\n        }\n      ]\n    },\n    {\n      \"headerName\": \"Bridge metrics\",\n      \"children\": [\n        {\n          \"headerName\": \"Creation date\",\n          \"field\": \"rawData.bridgeTimestamp\",\n          \"width\": 140\n        },\n        {\n          \"headerName\": \"Source\",\n          \"field\": \"source\",\n          \"width\": 50\n        },\n        {\n          \"field\": \"rawData.bridgerLogin\",\n          \"width\": 120,\n          \"headerName\": \"Bridger\"\n        },\n        {\n          \"field\": \"rawData.checkerLogin\",\n          \"width\": 120,\n          \"headerName\": \"Checker\"\n        },\n        {\n          \"field\": \"rawData.factor\",\n          \"width\": 80,\n          \"headerName\": \"Quantity factor\"\n        }\n      ]\n    }\n  ]\n}",
    bridgeRecordElasticConfig = ElasticsearchSimplifiedConfiguration(referenceFieldsUsedInElasticSearch = Seq(FieldConfiguration("brName1"), FieldConfiguration("brName2")))
  )




  //val Topic = "notificationTopic"//tableTopicName"//BasicTopic
  val Topic = "nuke-dev.configuration"//"person"//"local-dev.configuration"

  val rand = Random
  val producer = new KafkaProducer[String, String](ProducerConfigBuilder(bootstrapServer = "localhost:9092").buildConfig)


  val msg1 = "{\n  \"id\": 2,\n  \"url\": \"jdbc:sqlserver://warssoda02.internal.imsglobal.com:1433;databaseName=REST_STG\",\n  \"jdbcDriver\": \"com.microsoft.sqlserver.jdbc.SQLServerDriver\",\n  \"username\": \"reststg\",\n  \"password\": \"92eXqd_x_d\",\n  \"query\": \"SELECT CAST(BATCH_ID AS integer) AS batchId, \\n CAST(EMR_ITEM_ID AS NVARCHAR(1000)) AS id, \\n CAST(EMR_ID AS NVARCHAR(1000)) AS emrId, \\n CAST(PHA_CNT AS NVARCHAR(1000)) AS volume \\n FROM DBO.STG_TO_BRIDGE \\n WHERE LOAD_TMS < CONVERT(DATETIME, ':fromDatetime', 120) AND LOAD_TMS < CONVERT(DATETIME, ':toDatetime', 120)\",\n  \"queryParameters\": [\n    \"batchId\", \"fromDatetime\", \"toDatetime\"\n  ],\n  \"streamSparkApplicationConfiguration\": {\n    \"edgeNode\" : {\n      \"host\" : \"162.44.15.210\",\n      \"port\" : 22,\n      \"username\": \"mlcdusr\",\n      \"password\" : \"Mlcdevl1\"\n    },\n    \"jobPaths\" : {\n      \"localFsTmpPathRoot\" : \"/tmp/mlc/nuke-dev/importer-spark/\",\n      \"remoteFsTmpPathRoot\" : \"/development/mlc/data/nucleus/nuke-dev/tmp/importer-spark/\",\n      \"hdfsMetadataPathRoot\" : \"/development/mlc/data/nucleus/nuke-dev/importer-spark/\"\n    },\n    \"jobExecutionConfiguration\" : {\n      \"deployMode\" : \"cluster\",\n      \"master\" : \"yarn\",\n      \"poll\" : \"root.mlc\",\n      \"principal\" : \"mlcdusr@INTERNAL.IMSGLOBAL.COM\",\n      \"username\" : \"mlcdusr\"\n    }\n  }\n}"

  val msg2 = "{\"crossProcessUuid\":\"c89b48b4-aaaf-4ebd-8c48-5c218517e286\",\"nodeExecutionId\":25752,\"configurationId\":101,\"importParameters\":{\"batchId\":{\"string\":\"2018-10-23_00:00:00_86400\"},\"toDatetime\":{\"string\":\"2018-10-24 00:00:00\"},\"fromDatetime\":{\"string\":\"2018-10-23 00:00:00\"}}}"

  val msg3 = "{\"id\": 13, \"name\": \"John\", \"lastName\": \"Doe\"}"

  val msg4 = "{ \"crossProcessUuid\": \"Innrr\", \"createdAt\": 1545126139, \"dataOfferingStreamId\": 212, \"subConfiguration\": {\"string\": \"Mulit\"}, \"referenceId\": \"12\", \"factor\": 12, \"hdfsPath\": \"/path/sds\", \"rowCount\": 1, \"rowNumber\": 2, \"checker\": \"test1\", \"bridger\": \"test12\", \"sourceType\": \"MB\", \"bridgedAt\": 1545126139, \"referenceData\": { \"outletCityName\": \"SUMARE\", \"outletCnpj\": \"\", \"outletCnpjUploadDate\": \"\" }, \"rawData\": { \"checkerLogin\": \"bwardzinski\", \"proposal4RefId\": \"O601238\", \"proposal3Confidence\": \"7.339013176228547E-4\", \"proposal5Confidence\": \"2.7352486025028407E-4\", \"proposal5RefId\": \"O172623\" }, \"proposals\": {}, \"properties\": {} }"

  val configMsg = "{\"id\": 1, \"confName\": \"refmane111\"}"

  val msg5 = ""

  val msg12 = "{\"id\":2,\"subConfiguration\":\"multipleColumns\",\"countryName\":\"France\",\"dataOfferingName\":\"ne1Conf\",\"dataOfferingStreamName\":\"streamName\",\"displayConfig\":{\"columns\":[{\"headerName\":\"Raw Data\",\"children\":[{\"headerName\":\"Score\",\"field\":\"rawData.score\",\"pinned\":{\"string\":\"left\"},\"sort\":{\"string\":\"desc\"},\"width\":{\"int\":50},\"columnGroupShow\":null}]},{\"headerName\":\"Reference\",\"children\":[{\"headerName\":\"Organisation Id\",\"field\":\"referenceData.organisationId\",\"pinned\":{\"string\":\"left\"},\"sort\":{\"string\":\"desc\"},\"width\":{\"int\":50},\"columnGroupShow\":{\"string\":\"open\"}},{\"headerName\":\"Outlet name\",\"field\":\"referenceData.outletName\",\"pinned\":null,\"sort\":{\"string\":\"desc\"},\"width\":null,\"columnGroupShow\":null},{\"headerName\":\"Outlet type\",\"field\":\"referenceData.outletTyp\",\"pinned\":null,\"sort\":{\"string\":\"desc\"},\"width\":null,\"columnGroupShow\":null},{\"headerName\":\"Location id\",\"field\":\"eferenceData.locationId\",\"pinned\":null,\"sort\":{\"string\":\"desc\"},\"width\":null,\"columnGroupShow\":{\"string\":\"open\"}}]}]},\"referencesDisplayConfig\":{\"columns\":[{\"headerName\":\"Reference\",\"children\":[{\"headerName\":\"RDS Prescriber Initials\",\"field\":\"rdsPrescriberInitials\",\"pinned\":{\"string\":\"left\"},\"sort\":{\"string\":\"desc\"},\"width\":{\"int\":50},\"columnGroupShow\":{\"string\":\"open\"}},{\"headerName\":\"RDS Prescriber Last Name\",\"field\":\"rdsPrescriberLastName\",\"pinned\":null,\"sort\":{\"string\":\"desc\"},\"width\":null,\"columnGroupShow\":null}]}]},\"referenceElasticConfig\":{\"fieldsUsedInElasticSearch\":[{\"fieldName\":\"name1\",\"caseInsensitiveSort\":null},{\"fieldName\":\"name2\",\"caseInsensitiveSort\":null}],\"synonyms\":null},\"bridgeRecordElasticConfig\":{\"fieldsUsedInElasticSearch\":[{\"fieldName\":\"brName1\",\"caseInsensitiveSort\":null},{\"fieldName\":\"brName2\",\"caseInsensitiveSort\":null}],\"synonyms\":null}}"

  val msg13 = "{ \"configurationId\": 2, \"batchId\": \"\", \"factor\": 1.0, \"bridgerLogin\": \"system\", \"checkerLogin\": \"bwardzinski\", \"bridgeSource\": \"DC\", \"bridgeTimestamp\": 1523436499418, \"rawDataId\": \"AWKz1EtWnwPCsmsbEvCG\", \"referenceId\": \"451138\", \"referenceData\": {  \"rdsPracticeAddress3\": \"Dundalk\",  \"rdsPracticeLocationIdentifier\": \"null\",  \"rdsPrescriberPracticeLocationPrimaryIndicator\": \"0\",  \"rdsPracticeSuiteName\": \"null\",  \"rdsPracticeName\": \"Anne Smyth Pharmacy Ltd\",  \"rdsPracticePostalCode\": \"LOU1\",  \"rdsPracticeCity\": \"Dundalk\",  \"rdsAnonPrescriberIdentifier\": \"1930143819\",  \"rdsPrescriberInitials\": \"AR\",  \"score\": \"8.48\",  \"rdsPracticeAddress2\": \"null\",  \"rdsActive\": \"1\",  \"rdsUpdatedTimestamp\": \"2018-04-05 04:43:33\",  \"rdsPracticeCounty\": \"Co Louth\",  \"rdsActivityCenterName\": \"WUKF09271727\",  \"rdsActivityCentreTypeName\": \"Chemist\",  \"rdsPracticeCountryISOCode\": \"IE\",  \"rdsPracticeAddress1\": \"86 Clanbrassil Street\",  \"rdsPrescriberNationalReferenceNumber\": \"null\",  \"rdsPracticeBrickName\": \"DUNDALK\",  \"referenceId\": \"45738\",  \"rdsPracticeBuildingName\": \"null\",  \"rdsIgnore\": \"0\",  \"rdsPrescriberFirstName\": \"Anne\",  \"rdsPrescriberLastName\": \"Smyth\",  \"rdsPrescriberMiddleName\": \"null\",  \"rdsInsertedTimestamp\": \"2018-04-05 04:43:33\",  \"rdsInsertedBy\": \"IE9_BUREAU\",  \"rdsPracticeCountryName\": \"null\",  \"rdsPracticeStreetNumberText\": \"null\",  \"rdsPrescriberStatusName\": \"Invalid\",  \"rdsPrescriberProfessionSpecialtyTypeName\": \"Retail Chemist\" }, \"rawData\": {  \"addressLine1\": \"BELVEDERE SURGERY\",  \"checkerLogin\": \"bwardzinski\",  \"addressLine4\": \"CORK\",  \"firstname\": \"DIARMUID\",  \"score\": \"0.00\",  \"surname\": \"OCONNELL\",  \"factor\": \"1.0\",  \"addressLine3\": \"CORK\",  \"referenceId\": \"41045\",  \"bridgeTimestamp\": \"2018-04-11T08:48:19.418Z\",  \"addressLine2\": \"DOUGLAS ROAD\",  \"bridgerLogin\": \"system\",  \"anonymousPrescriberId\": \"3441\",  \"insertedTimestamp\": \"26-MAR-18 01.31.54.000000 PM\" } }"

  logger.error(s"${System.currentTimeMillis()}")
  Stream.from(1).map(createRecord).map(producer.send)
    .map(_.get()).toList

  private def createRecord(i: Int): ProducerRecord[String, String] = {
    Thread.sleep(Math.abs(rand.nextLong()%1000 * 1))
    val value = "someValue" + i
    val record = new ProducerRecord[String, String](Topic, s"bridgecatalog_2", msg12)
    record
  }
}
