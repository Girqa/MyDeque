package Project.Classes.AbstractClasses;

abstract public class ElementOfChain {
    private ElementOfChain prev;
    private ElementOfChain next;

    abstract public ElementOfChain getNext();
    abstract public ElementOfChain getPrev();
    abstract public ElementOfChain setNext();
    abstract public ElementOfChain setPrev();
}
