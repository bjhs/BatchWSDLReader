package pt.wsdl.reader

/**
 * Created by bjs on 17/12/2015.
 */
class ComplexType {
    String name;

    def element = [];

    ComplexType(def name, def element){
        this.name = name
        this.element = element
    }
}
