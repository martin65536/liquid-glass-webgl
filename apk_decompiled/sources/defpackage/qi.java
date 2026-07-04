package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class qi {
    public static final he0 a;

    static {
        wq0 wq0Var = af.e;
        int i = wq0Var.c;
        pi piVar = new pi(wq0Var, wq0Var, 1);
        int i2 = wq0Var.c;
        eh0 eh0Var = af.x;
        int i3 = (eh0Var.c << 6) | i2;
        pi piVar2 = new pi(wq0Var, eh0Var, 0);
        int i4 = (i2 << 6) | eh0Var.c;
        pi piVar3 = new pi(eh0Var, wq0Var, 0);
        he0 he0Var = u10.a;
        he0 he0Var2 = new he0();
        he0Var2.h(i | (i << 6), piVar);
        he0Var2.h(i3, piVar2);
        he0Var2.h(i4, piVar3);
        a = he0Var2;
    }
}
