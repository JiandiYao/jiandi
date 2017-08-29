package org.quickfix;

import java.util.List;

import org.fixb.FixSerializer;
import org.fixb.annotations.FixBlock;
import org.fixb.annotations.FixField;
import org.fixb.annotations.FixGroup;
import org.fixb.annotations.FixMessage;
import org.fixb.impl.NativeFixSerializer;
import org.fixb.meta.FixMetaDictionary;
import org.fixb.meta.FixMetaScanner;

@FixMessage(type = "Q")
public class FxQuote extends BaseQuote {

	public static void main(String[] args){
		FixMetaDictionary fixMetaDictionary = FixMetaScanner.scanClassesIn("my.fix.classes.package");
		FixSerializer<Object> fixSerializer = new NativeFixSerializer<>("FIX.5.0", fixMetaDictionary);
		
	}
    public static enum Side { BUY, SELL }

    @FixField(tag = 40)
    private final Side side;

    @FixField(tag = 12)
    private final String symbol;

    @FixGroup(tag = 13, componentTag = 14)
    private final List<Integer> amounts;

    @FixGroup(tag = 20)
    private final List<Params> paramsList;

    @FixBlock
    private final Params params;

    public FxQuote(
            final String quoteId,
            final Side side,
            final String symbol,
            final List<Integer> amounts,
            final List<Params> paramsList,
            final Params params) {
        super(quoteId);
        this.side = side;
        this.symbol = symbol;
        this.amounts = amounts;
        this.paramsList = paramsList;
        this.params = params;
    }

    public String getSymbol() {
        return symbol;
    }

    public Side getSide() {
        return side;
    }

    public List<Integer> getAmounts() {
        return amounts;
    }

    public Params getParams() {
        return params;
    }

    public List<Params> getParamsList() {
        return paramsList;
    }
}