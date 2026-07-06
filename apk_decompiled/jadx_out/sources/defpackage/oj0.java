package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class oj0 extends z30 implements gv {
    public static final oj0 g;
    public static final oj0 h;
    public static final oj0 i;
    public static final oj0 j;
    public static final oj0 k;
    public static final oj0 l;
    public static final oj0 m;
    public static final oj0 n;
    public final /* synthetic */ int f;

    static {
        int i2 = 1;
        g = new oj0(i2, 0);
        h = new oj0(i2, 1);
        i = new oj0(i2, 2);
        j = new oj0(i2, 3);
        k = new oj0(i2, 4);
        l = new oj0(i2, 5);
        m = new oj0(i2, 6);
        n = new oj0(i2, 7);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public oj0(int i2) {
        super(1);
        this.f = 8;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        int i2 = this.f;
        x31 x31Var = x31.a;
        switch (i2) {
            case 0:
                z40 z40Var = (z40) obj;
                if (z40Var.E()) {
                    z40.T(z40Var, false, 7);
                }
                return x31Var;
            case 1:
                z40 z40Var2 = (z40) obj;
                if (z40Var2.E()) {
                    z40Var2.C();
                }
                return x31Var;
            case 2:
                return x31Var;
            case 3:
                return x31Var;
            case 4:
                return Integer.valueOf(((gt0) obj).b);
            case 5:
                z10 z10Var = ((gt0) obj).c;
                return Integer.valueOf(z10Var.d - z10Var.b);
            case 6:
                return x31Var;
            case 7:
                throw null;
            default:
                return Boolean.valueOf(((pt) obj).D0());
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ oj0(int i2, int i3) {
        super(i2);
        this.f = i3;
    }
}
