package defpackage;

import android.graphics.PathMeasure;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ba0 extends z30 implements vu {
    public static final ba0 g;
    public static final ba0 h;
    public static final ba0 i;
    public static final ba0 j;
    public static final ba0 k;
    public static final ba0 l;
    public static final ba0 m;
    public static final ba0 n;
    public static final ba0 o;
    public static final ba0 p;
    public static final ba0 q;
    public static final ba0 r;
    public final /* synthetic */ int f;

    static {
        int i2 = 0;
        g = new ba0(i2, 0);
        h = new ba0(i2, 1);
        i = new ba0(i2, 2);
        j = new ba0(i2, 3);
        k = new ba0(i2, 4);
        l = new ba0(i2, 5);
        m = new ba0(i2, 6);
        n = new ba0(i2, 7);
        o = new ba0(i2, 8);
        p = new ba0(i2, 9);
        q = new ba0(i2, 10);
        r = new ba0(i2, 11);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ ba0(int i2, int i3) {
        super(i2);
        this.f = i3;
    }

    @Override // defpackage.vu
    public final Object a() {
        switch (this.f) {
            case 0:
                return new ad();
            case 1:
                return new sv0(se.b(se.b, 0.05f), 0.0f, 26);
            case 2:
                return new ad();
            case 3:
                return new sv0(se.b(se.b, 0.05f), 0.0f, 26);
            case 4:
                return new ad();
            case 5:
                return new y00(16.0f, 0.0f, 30);
            case 6:
                return new z5(new PathMeasure());
            case 7:
                return null;
            case 8:
                return o20.o;
            case 9:
                return new ek0(50.0f);
            case 10:
                return n30.B(Boolean.FALSE);
            default:
                return x31.a;
        }
    }
}
