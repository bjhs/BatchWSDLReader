package pt.wsdl.reader

/**
 * Created by bjs on 17/12/2015.
 */
class Element {
    def name
    def nillable
    def minOccurs
    def maxOccurs
    def type

    Element(def name, def nillable, def minOccurs, def maxOccurs, def type) {
        this.name = name
        this.nillable = nillable
        this.minOccurs = minOccurs
        this.maxOccurs = maxOccurs
        this.type = type
    }
}
