package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class af {
    public static final float[] a;
    public static final float[] b;
    public static final q21 c;
    public static final q21 d;
    public static final wq0 e;
    public static final wq0 f;
    public static final wq0 g;
    public static final wq0 h;
    public static final wq0 i;
    public static final wq0 j;
    public static final wq0 k;
    public static final wq0 l;
    public static final wq0 m;
    public static final wq0 n;
    public static final wq0 o;
    public static final wq0 p;
    public static final wq0 q;
    public static final wq0 r;
    public static final y30 s;
    public static final y30 t;
    public static final wq0 u;
    public static final wq0 v;
    public static final wq0 w;
    public static final eh0 x;
    public static final we[] y;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [eh0, we] */
    static {
        float[] fArr = {0.64f, 0.33f, 0.3f, 0.6f, 0.15f, 0.06f};
        a = fArr;
        float[] fArr2 = {0.67f, 0.33f, 0.21f, 0.71f, 0.14f, 0.08f};
        b = fArr2;
        float[] fArr3 = {0.708f, 0.292f, 0.17f, 0.797f, 0.131f, 0.046f};
        q21 q21Var = new q21(2.4d, 0.9478672985781991d, 0.05213270142180095d, 0.07739938080495357d, 0.04045d);
        q21 q21Var2 = new q21(2.2d, 0.9478672985781991d, 0.05213270142180095d, 0.07739938080495357d, 0.04045d);
        q21 q21Var3 = new q21(-3.0d, 2.0d, 2.0d, 5.591816309728916d, 0.28466892d, 0.55991073d, -0.685490157d);
        c = q21Var3;
        q21 q21Var4 = new q21(-2.0d, -1.555223d, 1.860454d, 0.012683313515655966d, 18.8515625d, -18.6875d, 6.277394636015326d);
        d = q21Var4;
        c61 c61Var = dl.o;
        wq0 wq0Var = new wq0("sRGB IEC61966-2.1", fArr, c61Var, q21Var, 0);
        e = wq0Var;
        wq0 wq0Var2 = new wq0("sRGB IEC61966-2.1 (Linear)", fArr, c61Var, 1.0d, 0.0f, 1.0f, 1);
        f = wq0Var2;
        wq0 wq0Var3 = new wq0("scRGB-nl IEC 61966-2-2:2003", fArr, c61Var, null, new v7(4), new v7(5), -0.799f, 2.399f, q21Var, 2);
        g = wq0Var3;
        wq0 wq0Var4 = new wq0("scRGB IEC 61966-2-2:2003", fArr, c61Var, 1.0d, -0.5f, 7.499f, 3);
        h = wq0Var4;
        wq0 wq0Var5 = new wq0("Rec. ITU-R BT.709-5", new float[]{0.64f, 0.33f, 0.3f, 0.6f, 0.15f, 0.06f}, c61Var, new q21(2.2222222222222223d, 0.9099181073703367d, 0.09008189262966333d, 0.2222222222222222d, 0.081d), 4);
        i = wq0Var5;
        wq0 wq0Var6 = new wq0("Rec. ITU-R BT.2020-1", new float[]{0.708f, 0.292f, 0.17f, 0.797f, 0.131f, 0.046f}, c61Var, new q21(2.2222222222222223d, 0.9096697898662786d, 0.09033021013372146d, 0.2222222222222222d, 0.08145d), 5);
        j = wq0Var6;
        wq0 wq0Var7 = new wq0("SMPTE RP 431-2-2007 DCI (P3)", new float[]{0.68f, 0.32f, 0.265f, 0.69f, 0.15f, 0.06f}, new c61(0.314f, 0.351f), 2.6d, 0.0f, 1.0f, 6);
        k = wq0Var7;
        wq0 wq0Var8 = new wq0("Display P3", new float[]{0.68f, 0.32f, 0.265f, 0.69f, 0.15f, 0.06f}, c61Var, q21Var, 7);
        l = wq0Var8;
        wq0 wq0Var9 = new wq0("NTSC (1953)", fArr2, dl.l, new q21(2.2222222222222223d, 0.9099181073703367d, 0.09008189262966333d, 0.2222222222222222d, 0.081d), 8);
        m = wq0Var9;
        wq0 wq0Var10 = new wq0("SMPTE-C RGB", new float[]{0.63f, 0.34f, 0.31f, 0.595f, 0.155f, 0.07f}, c61Var, new q21(2.2222222222222223d, 0.9099181073703367d, 0.09008189262966333d, 0.2222222222222222d, 0.081d), 9);
        n = wq0Var10;
        wq0 wq0Var11 = new wq0("Adobe RGB (1998)", new float[]{0.64f, 0.33f, 0.21f, 0.71f, 0.15f, 0.06f}, c61Var, 2.2d, 0.0f, 1.0f, 10);
        o = wq0Var11;
        wq0 wq0Var12 = new wq0("ROMM RGB ISO 22028-2:2013", new float[]{0.7347f, 0.2653f, 0.1596f, 0.8404f, 0.0366f, 1.0E-4f}, dl.m, new q21(1.8d, 1.0d, 0.0d, 0.0625d, 0.031248d), 11);
        p = wq0Var12;
        c61 c61Var2 = dl.n;
        wq0 wq0Var13 = new wq0("SMPTE ST 2065-1:2012 ACES", new float[]{0.7347f, 0.2653f, 0.0f, 1.0f, 1.0E-4f, -0.077f}, c61Var2, 1.0d, -65504.0f, 65504.0f, 12);
        q = wq0Var13;
        wq0 wq0Var14 = new wq0("Academy S-2014-004 ACEScg", new float[]{0.713f, 0.293f, 0.165f, 0.83f, 0.128f, 0.044f}, c61Var2, 1.0d, -65504.0f, 65504.0f, 13);
        r = wq0Var14;
        y30 y30Var = new y30(14, 1, 12884901889L, "Generic XYZ");
        s = y30Var;
        y30 y30Var2 = new y30(15, 0, 12884901890L, "Generic L*a*b*");
        t = y30Var2;
        wq0 wq0Var15 = new wq0("None", fArr, c61Var, q21Var2, 16);
        u = wq0Var15;
        wq0 wq0Var16 = new wq0("Hybrid Log Gamma encoding", fArr3, c61Var, null, new v7(6), new v7(7), 0.0f, 1.0f, q21Var3, 17);
        v = wq0Var16;
        wq0 wq0Var17 = new wq0("Perceptual Quantizer encoding", fArr3, c61Var, null, new v7(8), new v7(9), 0.0f, 1.0f, q21Var4, 18);
        w = wq0Var17;
        ?? weVar = new we("Oklab", 12884901890L, 19);
        x = weVar;
        y = new we[]{wq0Var, wq0Var2, wq0Var3, wq0Var4, wq0Var5, wq0Var6, wq0Var7, wq0Var8, wq0Var9, wq0Var10, wq0Var11, wq0Var12, wq0Var13, wq0Var14, y30Var, y30Var2, wq0Var15, wq0Var16, wq0Var17, weVar};
    }

    public static double a(q21 q21Var, double d2) {
        double d3;
        double exp;
        if (d2 < 0.0d) {
            d3 = -1.0d;
        } else {
            d3 = 1.0d;
        }
        double d4 = d2 * d3;
        double d5 = q21Var.b;
        double d6 = q21Var.c;
        double d7 = q21Var.d;
        double d8 = q21Var.e;
        double d9 = q21Var.f;
        double d10 = q21Var.g + 1.0d;
        double d11 = d5 * d4;
        if (d11 <= 1.0d) {
            exp = Math.pow(d11, d6);
        } else {
            exp = Math.exp((d4 - d9) * d7) + d8;
        }
        return d10 * d3 * exp;
    }

    public static double b(q21 q21Var, double d2) {
        double d3;
        double log;
        if (d2 < 0.0d) {
            d3 = -1.0d;
        } else {
            d3 = 1.0d;
        }
        double d4 = 1.0d / q21Var.b;
        double d5 = 1.0d / q21Var.c;
        double d6 = 1.0d / q21Var.d;
        double d7 = q21Var.e;
        double d8 = q21Var.f;
        double d9 = (d2 * d3) / (q21Var.g + 1.0d);
        if (d9 <= 1.0d) {
            log = Math.pow(d9, d5) * d4;
        } else {
            log = (Math.log(d9 - d7) * d6) + d8;
        }
        return d3 * log;
    }

    public static double c(q21 q21Var, double d2) {
        double d3;
        double d4 = 0.0d;
        if (d2 < 0.0d) {
            d3 = -1.0d;
        } else {
            d3 = 1.0d;
        }
        double d5 = d2 * d3;
        double d6 = q21Var.b;
        double d7 = q21Var.d;
        double pow = (Math.pow(d5, d7) * q21Var.c) + d6;
        if (pow >= 0.0d) {
            d4 = pow;
        }
        return Math.pow(d4 / ((Math.pow(d5, d7) * q21Var.f) + q21Var.e), q21Var.g) * d3;
    }

    public static double d(q21 q21Var, double d2) {
        double d3;
        if (d2 < 0.0d) {
            d3 = -1.0d;
        } else {
            d3 = 1.0d;
        }
        double d4 = d2 * d3;
        double d5 = -q21Var.b;
        double d6 = q21Var.e;
        double d7 = 1.0d / q21Var.g;
        return Math.pow(Math.max((Math.pow(d4, d7) * d6) + d5, 0.0d) / ((Math.pow(d4, d7) * (-q21Var.f)) + q21Var.c), 1.0d / q21Var.d) * d3;
    }
}
