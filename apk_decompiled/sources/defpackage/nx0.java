package defpackage;

import java.util.HashMap;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class nx0 {
    public final gv a;
    public Object b;
    public oe0 c;
    public boolean j;
    public int k;
    public int d = -1;
    public final ve0 e = t20.n();
    public final ve0 f = new ve0();
    public final we0 g = new we0();
    public final ef0 h = new ef0(new ym[16]);
    public final aw i = new aw(1, this);
    public final ve0 l = t20.n();
    public final HashMap m = new HashMap();

    public nx0(gv gvVar) {
        this.a = gvVar;
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    public final boolean a(java.util.Set r46) {
        /*
            Method dump skipped, instructions count: 1680
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.nx0.a(java.util.Set):boolean");
    }

    public final void b(Object obj, int i, Object obj2, oe0 oe0Var) {
        int i2;
        if (this.k <= 0) {
            int c = oe0Var.c(obj);
            if (c < 0) {
                c = ~c;
                i2 = -1;
            } else {
                i2 = oe0Var.c[c];
            }
            oe0Var.b[c] = obj;
            oe0Var.c[c] = i;
            if ((obj instanceof ym) && i2 != i) {
                xm h = ((ym) obj).h();
                this.m.put(obj, h.f);
                oe0 oe0Var2 = h.e;
                ve0 ve0Var = this.l;
                t20.J(ve0Var, obj);
                Object[] objArr = oe0Var2.b;
                long[] jArr = oe0Var2.a;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i3 = 0;
                    while (true) {
                        long j = jArr[i3];
                        if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i4 = 8 - ((~(i3 - length)) >>> 31);
                            for (int i5 = 0; i5 < i4; i5++) {
                                if ((j & 255) < 128) {
                                    ny0 ny0Var = (ny0) objArr[(i3 << 3) + i5];
                                    if (ny0Var instanceof oy0) {
                                        ((oy0) ny0Var).f(2);
                                    }
                                    t20.g(ve0Var, ny0Var, obj);
                                }
                                j >>= 8;
                            }
                            if (i4 != 8) {
                                break;
                            }
                        }
                        if (i3 == length) {
                            break;
                        } else {
                            i3++;
                        }
                    }
                }
            }
            if (i2 == -1) {
                if (obj instanceof oy0) {
                    ((oy0) obj).f(2);
                }
                t20.g(this.e, obj, obj2);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00d3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void c() {
        /*
            Method dump skipped, instructions count: 258
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.nx0.c():void");
    }
}
