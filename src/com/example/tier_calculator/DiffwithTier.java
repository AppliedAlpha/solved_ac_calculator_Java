package com.example.tier_calculator;

public class DiffwithTier {
    private static final int DIFFS_COUNT = 31;
    private static final int TIERS_COUNT = 30;
    public static Diff[] diffs = new Diff[31];
    public static Tier[] tiers = new Tier[30];

    public DiffwithTier() {
    }

    public static void init() {
        initDiff();
        initTier();
    }

    private static void initDiff() {
        diffs[0] = new Diff("Unrated", 256);
        diffs[1] = new Diff("Bronze V", 480);
        diffs[2] = new Diff("Bronze IV", 672);
        diffs[3] = new Diff("Bronze III", 954);
        diffs[4] = new Diff("Bronze II", 1374);
        diffs[5] = new Diff("Bronze I", 1992);
        diffs[6] = new Diff("Silver V", 2909);
        diffs[7] = new Diff("Silver IV", 4276);
        diffs[8] = new Diff("Silver III", 6329);
        diffs[9] = new Diff("Silver II", 9430);
        diffs[10] = new Diff("Silver I", 14145);
        diffs[11] = new Diff("Gold V", 21288);
        diffs[12] = new Diff("Gold IV", 32145);
        diffs[13] = new Diff("Gold III", 48699);
        diffs[14] = new Diff("Gold II", 74023);
        diffs[15] = new Diff("Gold I", 112885);
        diffs[16] = new Diff("Platinum V", 172714);
        diffs[17] = new Diff("Platinum IV", 265117);
        diffs[18] = new Diff("Platinum III", 408280);
        diffs[19] = new Diff("Platinum II", 630792);
        diffs[20] = new Diff("Platinum I", 977727);
        diffs[21] = new Diff("Diamond V", 1520366);
        diffs[22] = new Diff("Diamond IV", 2371771);
        diffs[23] = new Diff("Diamond III", 3711822);
        diffs[24] = new Diff("Diamond II", 5827560);
        diffs[25] = new Diff("Diamond I", 9178407);
        diffs[26] = new Diff("Ruby V", 14501883);
        diffs[27] = new Diff("Ruby IV", 22985485);
        diffs[28] = new Diff("Ruby III", 36546921);
        diffs[29] = new Diff("Ruby II", 58292339);
        diffs[30] = new Diff("Ruby I", 93267742);
    }

    private static void initTier() {
        tiers[0] = new Tier(1, "Bronze V", 0L, 9590L);
        tiers[1] = new Tier(2, "Bronze IV", 9590L, 23030L);
        tiers[2] = new Tier(3, "Bronze III", 23030L, 42110L);
        tiers[3] = new Tier(4, "Bronze II", 42110L, 69590L);
        tiers[4] = new Tier(5, "Bronze I", 69590L, 109430L);
        tiers[5] = new Tier(6, "Silver V", 109430L, 182155L);
        tiers[6] = new Tier(7, "Silver IV", 182155L, 289055L);
        tiers[7] = new Tier(8, "Silver III", 289055L, 447280L);
        tiers[8] = new Tier(9, "Silver II", 447280L, 683030L);
        tiers[9] = new Tier(10, "Silver I", 683030L, 1036655L);
        tiers[10] = new Tier(11, "Gold V", 1036655L, 1675295L);
        tiers[11] = new Tier(12, "Gold IV", 1675295L, 2639645L);
        tiers[12] = new Tier(13, "Gold III", 2639645L, 4100615L);
        tiers[13] = new Tier(14, "Gold II", 4100615L, 6321305L);
        tiers[14] = new Tier(15, "Gold I", 6321305L, 10836705L);
        tiers[15] = new Tier(16, "Platinum V", 10836705L, 16018125L);
        tiers[16] = new Tier(17, "Platinum IV", 16018125L, 23971635L);
        tiers[17] = new Tier(18, "Platinum III", 23971635L, 36220035L);
        tiers[18] = new Tier(19, "Platinum II", 36220035L, 55143795L);
        tiers[19] = new Tier(20, "Platinum I", 55143795L, 84475605L);
        tiers[20] = new Tier(21, "Diamond V", 84475605L, 130086585L);
        tiers[21] = new Tier(22, "Diamond IV", 130086585L, 201244515L);
        tiers[22] = new Tier(23, "Diamond III", 201244515L, 312599175L);
        tiers[23] = new Tier(24, "Diamond II", 312599175L, 487425975L);
        tiers[24] = new Tier(25, "Diamond I", 487425975L, 854562255L);
        tiers[25] = new Tier(26, "Ruby V", 854562255L, 1434637575L);
        tiers[26] = new Tier(27, "Ruby IV", 1434637575L, 2354056975L);
        tiers[27] = new Tier(28, "Ruby III", 2354056975L, 3815933815L);
        tiers[28] = new Tier(29, "Ruby II", 3815933815L, 6147627375L);
        tiers[29] = new Tier(30, "Ruby I", 6147627375L, -1L);
    }
}
