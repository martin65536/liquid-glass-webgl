package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class lf0 extends sz0 implements kv {
    public qf0 i;
    public Object j;
    public of0 k;
    public int l;
    public /* synthetic */ Object m;
    public final /* synthetic */ of0 n;
    public final /* synthetic */ gv o;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public lf0(of0 of0Var, gv gvVar, ij ijVar) {
        super(2, ijVar);
        this.n = of0Var;
        this.o = gvVar;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        return ((lf0) i((ij) obj2, (hk) obj)).k(x31.a);
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        lf0 lf0Var = new lf0(this.n, this.o, ijVar);
        lf0Var.m = obj;
        return lf0Var;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: ConstInlineVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Unexpected instance arg in invoke
        	at jadx.core.dex.visitors.ConstInlineVisitor.addExplicitCast(ConstInlineVisitor.java:285)
        	at jadx.core.dex.visitors.ConstInlineVisitor.replaceArg(ConstInlineVisitor.java:267)
        	at jadx.core.dex.visitors.ConstInlineVisitor.replaceConst(ConstInlineVisitor.java:177)
        	at jadx.core.dex.visitors.ConstInlineVisitor.checkInsn(ConstInlineVisitor.java:110)
        	at jadx.core.dex.visitors.ConstInlineVisitor.process(ConstInlineVisitor.java:55)
        	at jadx.core.dex.visitors.ConstInlineVisitor.visit(ConstInlineVisitor.java:47)
        */
    @Override // defpackage.s9
    public final java.lang.Object k(java.lang.Object r9) {
        /*
            Method dump skipped, instructions count: 230
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.lf0.k(java.lang.Object):java.lang.Object");
    }
}
