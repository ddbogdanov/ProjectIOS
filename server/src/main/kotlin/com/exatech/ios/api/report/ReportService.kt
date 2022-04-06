package com.exatech.ios.api.report

import com.exatech.ios.api.productionmaterial.calculated.CalculatedMaterial
import com.exatech.ios.api.productionmaterial.calculated.CalculatedMaterialService
import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.HtmlConverter
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Service
class ReportService(val cms: CalculatedMaterialService) {

    fun generateMaterialReport(inString: String): ByteArray? {
        val calculatedMaterials = cms.findAll()
        val leastAmountMaterial = calculatedMaterials.minWithOrNull(Comparator.comparingDouble { it.amount }) ?: return null
        val mostAmountMaterial = calculatedMaterials.maxWithOrNull(Comparator.comparingDouble { it.amount }) ?: return null


        val htmlFile = File("server/src/main/resources/reports/material/material-report-template.html")
        val doc: Document = Jsoup.parse(htmlFile.readText())

        doc.getElementById("timestamp")?.text("Generated On: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")))

        // Set labels for highest quantity material
        doc.getElementById("most-name")?.text(mostAmountMaterial.name)
        doc.getElementById("most-color")?.text("Color: " + mostAmountMaterial.color.color)
        doc.getElementById("most-type")?.text("Type: " + mostAmountMaterial.materialType.type)
        doc.getElementById("most-amount")?.text("Amount: " + mostAmountMaterial.amount)

        // Set labels for lowest quantity material
        doc.getElementById("least-name")?.text(leastAmountMaterial.name)
        doc.getElementById("least-color")?.text("Color: " + leastAmountMaterial.color.color)
        doc.getElementById("least-type")?.text("Type: " + leastAmountMaterial.materialType.type)
        doc.getElementById("least-amount")?.text("Amount: " + leastAmountMaterial.amount)

        // Populate table with materials
        for(m: CalculatedMaterial in calculatedMaterials) {
            doc.getElementById("table-body")?.append("<tr><td>" + m.name + "</td><td>" + m.materialType.type + "</td><td>" + m.manufacturer.manufacturer + "</td><td>" + m.color.color + "</td><td>" + m.amount + "</td>")
        }


        val converterProperties = ConverterProperties()
        converterProperties.baseUri = "./server/src/main/resources/reports/material"
        HtmlConverter.convertToPdf(
            doc.outerHtml(),
            FileOutputStream("server/src/main/kotlin/com/exatech/ios/api/report/anpdf.pdf"),
            converterProperties)

        return ByteArray(1)
    }
}


/*FileOutputStream("server/src/main/kotlin/com/exatech/ios/api/report/anpdf.pdf").use { os ->
            val baseUri = FileSystems.getDefault()
                .getPath("./server/src/main/resources/reports/material")
                .toUri()
                .toString()
            val builder = PdfRendererBuilder()
            builder.withUri("server/src/main/kotlin/com/exatech/ios/api/report/anpdf.pdf")
            builder.toStream(os)
            builder.withW3cDocument(W3CDom().fromJsoup(doc), baseUri)
            builder.run()
        }*/