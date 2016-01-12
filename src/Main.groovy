import groovy.io.FileType

/**
 * Created by bjs on 18/12/2015.
 */
class Main {

    public static void main (String[] args) {

        def batchWSDLReader = new BatchWSDLReader()
        def services

        def dir = new File(args[0])
        dir.eachFileRecurse (FileType.FILES) { file ->
            println 'Reading file '+file
            services = batchWSDLReader.readFile(file)
        }
        println 'Generating XML file...'
        batchWSDLReader.createXML(args[0]+'Output', services)
    }
}
