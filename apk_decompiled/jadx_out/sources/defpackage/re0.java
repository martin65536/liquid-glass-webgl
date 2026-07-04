package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class re0 extends iq0 implements kv {
    public iw g;
    public se0 h;
    public long[] i;
    public int j;
    public int k;
    public /* synthetic */ Object l;
    public final /* synthetic */ se0 m;
    public final /* synthetic */ iw n;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public re0(se0 se0Var, iw iwVar, ij ijVar) {
        super(ijVar);
        this.m = se0Var;
        this.n = iwVar;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        return ((re0) i((ij) obj2, (mv0) obj)).k(x31.a);
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        re0 re0Var = new re0(this.m, this.n, ijVar);
        re0Var.l = obj;
        return re0Var;
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        mv0 mv0Var;
        se0 se0Var;
        long[] jArr;
        int i;
        iw iwVar;
        int i2 = this.k;
        if (i2 != 0) {
            if (i2 == 1) {
                i = this.j;
                jArr = this.i;
                se0Var = this.h;
                iwVar = this.g;
                mv0Var = (mv0) this.l;
                o30.x(obj);
            } else {
                v7.o("call to 'resume' before 'invoke' with coroutine");
                return null;
            }
        } else {
            o30.x(obj);
            mv0Var = (mv0) this.l;
            se0Var = this.m;
            qe0 qe0Var = se0Var.f;
            jArr = qe0Var.c;
            i = qe0Var.e;
            iwVar = this.n;
        }
        if (i != Integer.MAX_VALUE) {
            int i3 = (int) ((jArr[i] >> 31) & 2147483647L);
            iwVar.f = i;
            Object obj2 = se0Var.f.b[i];
            this.l = mv0Var;
            this.g = iwVar;
            this.h = se0Var;
            this.i = jArr;
            this.j = i3;
            this.k = 1;
            mv0Var.b(this, obj2);
            return ik.e;
        }
        return x31.a;
    }
}
