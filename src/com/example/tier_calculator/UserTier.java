package com.example.tier_calculator;

public class UserTier {
    private long exp = 0L;
    private Tier tier = new Tier();

    public UserTier(long exp) {
        this.exp = exp;
        this.calcTier();
    }

    private void calcTier() {
        Tier[] var4;
        int var3 = (var4 = DiffwithTier.tiers).length;

        for(int var2 = 0; var2 < var3; ++var2) {
            Tier t = var4[var2];
            if (this.exp < t.getMinEXP()) {
                break;
            }

            this.tier = t;
        }

    }

    protected Tier getTier() {
        return this.tier;
    }

    protected UserTier.UserEXP getUserTier() {
        return new UserTier.UserEXP(this.exp - this.tier.getMinEXP(), this.tier.getMaxEXP() - this.tier.getMinEXP());
    }

    class UserEXP {
        private long curEXP = 0L;
        private long mEXP = 0L;
        private double per = 0.0D;

        public UserEXP() {
        }

        public UserEXP(long cur, long m) {
            this.curEXP = cur;
            if (UserTier.this.tier.getTierLevel() == DiffwithTier.tiers.length) {
                this.per = 100.0D;
                m = 0L;
            } else {
                this.mEXP = m;
                this.per = (double)cur / (double)m * 100.0D;
            }

        }

        protected long getCurEXP() {
            return this.curEXP;
        }

        protected long getMEXP() {
            return this.mEXP;
        }

        protected double getPer() {
            return this.per;
        }
    }
}
