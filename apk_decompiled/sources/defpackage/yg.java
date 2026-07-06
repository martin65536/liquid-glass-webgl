package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class yg extends iq0 implements kv {
    public int g;
    public int h;
    public int i;
    public int j;
    public /* synthetic */ Object k;
    public final /* synthetic */ zg l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public yg(zg zgVar, ij ijVar) {
        super(ijVar);
        this.l = zgVar;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        return ((yg) i((ij) obj2, (mv0) obj)).k(x31.a);
    }

    @Override // defpackage.s9
    public final ij i(ij ijVar, Object obj) {
        yg ygVar = new yg(this.l, ijVar);
        ygVar.k = obj;
        return ygVar;
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        mv0 mv0Var;
        int i;
        int i2;
        int i3;
        String str;
        int i4;
        int i5;
        String str2;
        zg zgVar = this.l;
        pe0 pe0Var = zgVar.e;
        ge0 ge0Var = zgVar.g;
        int i6 = this.j;
        if (i6 != 0) {
            if (i6 == 1) {
                i = this.i;
                i2 = this.h;
                i3 = this.g;
                mv0Var = (mv0) this.k;
                o30.x(obj);
            } else {
                v7.o("call to 'resume' before 'invoke' with coroutine");
                return null;
            }
        } else {
            o30.x(obj);
            mv0Var = (mv0) this.k;
            i = 0;
            i2 = 0;
            i3 = 0;
        }
        if (i3 < Math.min(zgVar.h + 10, ge0Var.b)) {
            int i7 = i3 + 1;
            int b = ge0Var.b(i3);
            switch (b) {
                case 0:
                    str = "up";
                    break;
                case 1:
                    Object f = pe0Var.f(i2);
                    i2++;
                    str = "down " + f;
                    break;
                case 2:
                    str = "remove " + ge0Var.b(i7) + ' ' + ge0Var.b(i3 + 2);
                    i7 = i3 + 3;
                    break;
                case 3:
                    str = "move " + ge0Var.b(i7) + ' ' + ge0Var.b(i3 + 2) + ' ' + ge0Var.b(i3 + 3);
                    i7 = i3 + 4;
                    break;
                case 4:
                    str = "clear";
                    break;
                case 5:
                    i4 = i3 + 2;
                    int b2 = ge0Var.b(i7);
                    i5 = i2 + 1;
                    str2 = "insertBottomUp " + b2 + ' ' + pe0Var.f(i2);
                    int i8 = i4;
                    str = str2;
                    i7 = i8;
                    i2 = i5;
                    break;
                case 6:
                    i4 = i3 + 2;
                    int b3 = ge0Var.b(i7);
                    i5 = i2 + 1;
                    str2 = "insertTopDown " + b3 + ' ' + pe0Var.f(i2);
                    int i82 = i4;
                    str = str2;
                    i7 = i82;
                    i2 = i5;
                    break;
                case 7:
                    Object f2 = pe0Var.f(i2);
                    f2.getClass();
                    f31.n(2, f2);
                    i2 += 2;
                    str = "apply " + ((kv) f2);
                    break;
                case 8:
                    str = "reuse " + zgVar.f.f(i);
                    i++;
                    break;
                case 9:
                    str = "recompose pending";
                    break;
                default:
                    str = "unknown op: " + b;
                    break;
            }
            this.k = mv0Var;
            this.g = i7;
            this.h = i2;
            this.i = i;
            this.j = 1;
            mv0Var.b(this, i3 + ": " + str);
            return ik.e;
        }
        return x31.a;
    }
}
