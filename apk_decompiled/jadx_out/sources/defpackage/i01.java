package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class i01 extends iq0 implements kv {
    public int g;
    public /* synthetic */ Object h;
    public final /* synthetic */ hk i;
    public final /* synthetic */ mn0 j;
    public final /* synthetic */ lv k;
    public final /* synthetic */ x90 l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public i01(hk hkVar, mn0 mn0Var, lv lvVar, x90 x90Var, ij ijVar) {
        super(ijVar);
        this.i = hkVar;
        this.j = mn0Var;
        this.k = lvVar;
        this.l = x90Var;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        return ((i01) i((ij) obj2, (xz0) obj)).k(x31.a);
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        i01 i01Var = new i01(this.i, this.j, this.k, this.l, ijVar);
        i01Var.h = obj;
        return i01Var;
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        int i = this.g;
        if (i != 0) {
            if (i == 1) {
                o30.x(obj);
            } else {
                v7.o("call to 'resume' before 'invoke' with coroutine");
                return null;
            }
        } else {
            o30.x(obj);
            xz0 xz0Var = (xz0) this.h;
            this.g = 1;
            Object f = o01.f(xz0Var, this.i, this.j, this.k, this.l, this);
            ik ikVar = ik.e;
            if (f == ikVar) {
                return ikVar;
            }
        }
        return x31.a;
    }
}
