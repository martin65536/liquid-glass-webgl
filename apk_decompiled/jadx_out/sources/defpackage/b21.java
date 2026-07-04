package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class b21 {
    public final int a;
    public final w8 b;
    public final c c;
    public b21 d;
    public long e;
    public long f;
    public long g = Long.MIN_VALUE;
    public final /* synthetic */ c21 h;

    public b21(c21 c21Var, int i, w8 w8Var, c cVar) {
        this.h = c21Var;
        this.a = i;
        this.b = w8Var;
        this.c = cVar;
    }

    public final void a(long j, long j2, long j3, long j4, float[] fArr) {
        jp0 jp0Var;
        jp0 jp0Var2;
        long j5 = this.h.f;
        w8 w8Var = this.b;
        ng0 B = k81.B(w8Var, 2);
        z40 E = k81.E(w8Var);
        boolean F = E.F();
        lg0 lg0Var = E.H;
        if (!F) {
            jp0Var2 = null;
        } else {
            if (lg0Var.d != B) {
                long floatToRawIntBits = (Float.floatToRawIntBits((int) (j & 4294967295L)) & 4294967295L) | (Float.floatToRawIntBits((int) (j >> 32)) << 32);
                long j6 = B.g;
                ng0 ng0Var = lg0Var.d;
                ng0Var.getClass();
                jp0Var = new jp0(f31.L(ng0Var.R(B, floatToRawIntBits)), (4294967295L & (((int) (r3 & 4294967295L)) + ((int) (j6 & 4294967295L)))) | ((((int) (r3 >> 32)) + ((int) (j6 >> 32))) << 32), j3, j4, j5, fArr, w8Var);
            } else {
                jp0Var = new jp0(j, j2, j3, j4, j5, fArr, w8Var);
            }
            jp0Var2 = jp0Var;
        }
        if (jp0Var2 == null) {
            return;
        }
        this.c.e(jp0Var2);
    }

    public final void b() {
        b21 b21Var;
        c21 c21Var = this.h;
        he0 he0Var = c21Var.a;
        int i = this.a;
        b21 b21Var2 = (b21) he0Var.g(i);
        if (b21Var2 != null) {
            if (b21Var2 != this) {
                int d = he0Var.d(i);
                Object[] objArr = he0Var.c;
                Object obj = objArr[d];
                he0Var.b[d] = i;
                objArr[d] = b21Var2;
                while (true) {
                    b21 b21Var3 = b21Var2.d;
                    if (b21Var3 == null) {
                        break;
                    }
                    if (b21Var3 == this) {
                        b21Var2.d = this.d;
                        this.d = null;
                        return;
                    }
                    b21Var2 = b21Var3;
                }
            } else {
                b21 b21Var4 = this.d;
                this.d = null;
                if (b21Var4 != null) {
                    int d2 = he0Var.d(i);
                    Object[] objArr2 = he0Var.c;
                    Object obj2 = objArr2[d2];
                    he0Var.b[d2] = i;
                    objArr2[d2] = b21Var4;
                    return;
                }
                z40 E = k81.E(this.b.e);
                if (E.k) {
                    ((b4) c50.a(E)).getRectManager().b.f(E.f, false);
                    return;
                }
                return;
            }
        }
        b21 b21Var5 = c21Var.b;
        if (b21Var5 == this) {
            c21Var.b = b21Var5.d;
            this.d = null;
            return;
        }
        if (b21Var5 != null) {
            b21Var = b21Var5.d;
        } else {
            b21Var = null;
        }
        while (true) {
            b21 b21Var6 = b21Var5;
            b21Var5 = b21Var;
            if (b21Var5 != null) {
                if (b21Var5 == this) {
                    if (b21Var6 != null) {
                        b21Var6.d = b21Var5.d;
                    }
                    this.d = null;
                    return;
                }
                b21Var = b21Var5.d;
            } else {
                return;
            }
        }
    }
}
