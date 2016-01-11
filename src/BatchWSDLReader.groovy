import groovy.json.*
import groovy.xml.*
import pt.wsdl.reader.*


/**
 * Created by bjs on 17/12/2015.
 */
class BatchWSDLReader {
    private services = []

    def readFile(file) {

        FileInputStream fileInput = new FileInputStream(file)

        def xmlContent = new XmlSlurper().parse(fileInput)
        def service = new Service(name: xmlContent.service.@name)

        //output
        xmlContent.types.schema.each{
            s -> s.complexType.each {ct ->
                if (ct.@name != null &&
                        (ct.@name.text().toLowerCase().contains('output')
                                || ct.@name.text().toLowerCase().contains('response')
                                || ct.@name.text().toLowerCase().contains('result')
                                || ct.@name.text().toLowerCase().contains('ouput'))) {

                    def elements = []
                    ct.sequence.element.each{ e ->
                        elements.add(new Element(e.@name.text(),
                                e.@nillable.text(),
                                e.@minOccurs.text(),
                                e.@maxOccurs.text(),
                                e.@type.text()
                        ))
                    }

                    def complexType = new ComplexType(ct.@name.text(), elements)
                    service.outputTypes.add(JsonOutput.prettyPrint( JsonOutput.toJson(complexType)))
                }
            }
        }
        //input
        xmlContent.types.schema.each{
            s -> s.complexType.each {
                ct ->  if (ct.@name != null &&
                        (!ct.@name.text().toLowerCase().contains('output')
                                && !ct.@name.text().toLowerCase().contains('response')
                                && !ct.@name.text().toLowerCase().contains('result')
                                && !ct.@name.text().toLowerCase().contains('ouput'))) {
                    def elements = []
                    ct.sequence.element.each{ e ->
                        elements.add(new Element(e.@name.text(),
                                e.@nillable.text(),
                                e.@minOccurs.text(),
                                e.@maxOccurs.text(),
                                e.@type.text()
                        ))
                    }

                    def complexType = new ComplexType(ct.@name.text(), elements)
                    service.inputTypes.add(JsonOutput.prettyPrint( JsonOutput.toJson(complexType)))
                }
            }
        }

        services.add(service)

        return services

    }


    static void createXML(path, services) {

        OutputStream fileOutput = new FileOutputStream(path+"/result.xml")
        def xml = new MarkupBuilder(new PrintWriter(fileOutput))
        xml.services {
            services.each { s ->
                service {
                    name(s.name)
                    s.inputTypes.each { i ->
                        inputType(i)
                    }
                    s.outputTypes.each { o ->
                        outputType(o)
                    }
                }
            }
        }
    }
}