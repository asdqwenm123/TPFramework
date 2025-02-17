package kr.tpmc.shop;

public enum ShopBuyingType {
    LEFT_CLICK(1, TradeType.BUY),
    RIGHT_CLICK(1, TradeType.SELL),
    SHIFT_RIGHT_CLICK(64, TradeType.BUY),
    SHIFT_LEFT_CLICK(64, TradeType.SELL),;

    private final int amount;
    private final TradeType tradeType;

    ShopBuyingType(int amount, TradeType tradeType) {
        this.amount = amount;
        this.tradeType = tradeType;
    }

    public int getAmount() {
        return amount;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public enum TradeType {
        BUY,
        SELL
    }
}
