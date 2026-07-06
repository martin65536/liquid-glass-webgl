package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class p71 {
    public static final he0 a;
    public static final n71[] b;

    static {
        he0 he0Var = new he0(8);
        n71.a.getClass();
        o71 o71Var = m71.g;
        he0Var.h(1, o71Var);
        o71 o71Var2 = m71.f;
        he0Var.h(2, o71Var2);
        o71 o71Var3 = m71.b;
        he0Var.h(4, o71Var3);
        o71 o71Var4 = m71.d;
        he0Var.h(8, o71Var4);
        o71 o71Var5 = m71.h;
        he0Var.h(16, o71Var5);
        o71 o71Var6 = m71.e;
        he0Var.h(32, o71Var6);
        o71 o71Var7 = m71.i;
        he0Var.h(64, o71Var7);
        o71 o71Var8 = m71.c;
        he0Var.h(128, o71Var8);
        a = he0Var;
        b = new n71[]{o71Var, o71Var2, o71Var3, o71Var7, o71Var5, o71Var6, o71Var4, m71.j, o71Var8};
    }

    public static final void a(lb0 lb0Var, x00 x00Var, long j, int i, int i2) {
        if (!y20.g(j, -1L)) {
            lb0Var.r(x00Var.b(), (int) ((j >>> 48) & 65535));
            lb0Var.r(x00Var.d(), (int) ((j >>> 32) & 65535));
            lb0Var.r(x00Var.c(), i - ((int) ((j >>> 16) & 65535)));
            lb0Var.r(x00Var.a(), i2 - ((int) (j & 65535)));
        }
    }
}
