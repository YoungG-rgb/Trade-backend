package kg.tech.tradebackend.utils.excel;

public interface IColumnMetadata {

    public String getName();

    public Object getValue(Object obj);

    public int getOrder();

    public boolean isIgnored();

    public String getHeader();
}
