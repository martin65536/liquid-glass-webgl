package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class yu0 extends z30 implements kv {
    public static final yu0 g;
    public static final yu0 h;
    public static final yu0 i;
    public final /* synthetic */ int f;

    static {
        int i2 = 2;
        g = new yu0(i2, 0);
        h = new yu0(i2, 1);
        i = new yu0(i2, 2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ yu0(int i2, int i3, boolean z) {
        super(2);
        this.f = i3;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        String str;
        sv svVar;
        int i2 = this.f;
        x31 x31Var = x31.a;
        switch (i2) {
            case 0:
                n0 n0Var = (n0) obj;
                n0 n0Var2 = (n0) obj2;
                if (n0Var == null || (str = n0Var.a) == null) {
                    str = n0Var2.a;
                }
                if (n0Var == null || (svVar = n0Var.b) == null) {
                    svVar = n0Var2.b;
                }
                return new n0(str, svVar);
            case 1:
                if (obj == null) {
                    return obj2;
                }
                return obj;
            case 2:
                su0 su0Var = (su0) obj2;
                Object valueOf = Float.valueOf(0.0f);
                nu0 nu0Var = ((su0) obj).d;
                av0 av0Var = wu0.t;
                Object g2 = nu0Var.e.g(av0Var);
                if (g2 == null) {
                    g2 = valueOf;
                }
                float floatValue = ((Number) g2).floatValue();
                Object g3 = su0Var.d.e.g(av0Var);
                if (g3 != null) {
                    valueOf = g3;
                }
                return Integer.valueOf(Float.compare(floatValue, ((Number) valueOf).floatValue()));
            case 3:
                ((Number) obj2).intValue();
                jc0.a((bw) obj, d20.O(1));
                return x31Var;
            case 4:
                ((Number) obj2).intValue();
                dl.c((bw) obj, d20.O(1));
                return x31Var;
            case 5:
                ((Number) obj2).intValue();
                o4.b((bw) obj, d20.O(1));
                return x31Var;
            case 6:
                ((Number) obj2).intValue();
                n20.b((bw) obj, d20.O(1));
                return x31Var;
            case 7:
                ((Number) obj2).intValue();
                n20.c((bw) obj, d20.O(1));
                return x31Var;
            case 8:
                ((Number) obj2).intValue();
                dl.h((bw) obj, d20.O(1));
                return x31Var;
            case 9:
                ((Number) obj2).intValue();
                n30.a((bw) obj, d20.O(1));
                return x31Var;
            case 10:
                ((Number) obj2).intValue();
                t20.b((bw) obj, d20.O(1));
                return x31Var;
            case 11:
                ((Number) obj2).intValue();
                g30.d((bw) obj, d20.O(1));
                return x31Var;
            case 12:
                ((Number) obj2).intValue();
                n30.c((bw) obj, d20.O(1));
                return x31Var;
            case 13:
                ((Number) obj2).intValue();
                g30.f((bw) obj, d20.O(1));
                return x31Var;
            case 14:
                ((Number) obj2).intValue();
                g30.g((bw) obj, d20.O(1));
                return x31Var;
            case 15:
                ((Number) obj2).intValue();
                d20.a((bw) obj, d20.O(1));
                return x31Var;
            default:
                ((Number) obj2).intValue();
                g30.h((bw) obj, d20.O(1));
                return x31Var;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ yu0(int i2, int i3) {
        super(i2);
        this.f = i3;
    }
}
