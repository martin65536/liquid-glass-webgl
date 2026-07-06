package defpackage;

import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class p21 extends ug0 {
    public final zb f;
    public dy0 g;

    public p21(hu0 hu0Var, fg fgVar, mm mmVar) {
        super(hu0Var, fgVar, mmVar);
        this.f = f31.c(Integer.MAX_VALUE, 6, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x00dd, code lost:
    
        if (r0.d(r3, r7) != r10) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00df, code lost:
    
        return r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00b6, code lost:
    
        if (r16.b(r0, r7) == r10) goto L25;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x002f  */
    /* JADX WARN: Type inference failed for: r3v4, types: [ep0, java.lang.Object] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.Object c(defpackage.p21 r16, defpackage.hu0 r17, defpackage.n21 r18, defpackage.jj r19) {
        /*
            Method dump skipped, instructions count: 227
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.p21.c(p21, hu0, n21, jj):java.lang.Object");
    }

    public static n21 e(zb zbVar) {
        n21 n21Var = null;
        mv0 y = g30.y(new su(new pd0(zbVar, 1), null));
        while (y.hasNext()) {
            n21 n21Var2 = (n21) y.next();
            if (n21Var != null) {
                n21Var2 = n21Var.a(n21Var2);
            }
            n21Var = n21Var2;
        }
        return n21Var;
    }

    public final boolean d(pm0 pm0Var) {
        boolean z;
        boolean z2;
        boolean z3;
        zb zbVar;
        hu0 hu0Var;
        boolean z4;
        boolean z5;
        boolean z6;
        um0 um0Var = (um0) me.T(pm0Var.a);
        if (um0Var != null) {
            List list = um0Var.m;
            if (list == null) {
                list = er.e;
            }
            int size = list.size();
            int i = 0;
            z3 = false;
            while (true) {
                zbVar = this.f;
                hu0Var = this.a;
                if (i >= size) {
                    break;
                }
                ly lyVar = (ly) list.get(i);
                long j = lyVar.d ^ (-9223372034707292160L);
                if (hu0Var.i(hu0Var.e(j)) == 0.0f) {
                    z6 = true;
                } else {
                    z6 = false;
                }
                if (!z6) {
                    if ((zbVar.q(new n21(j, lyVar.a, false)) instanceof nd) && !z3) {
                        z3 = false;
                    } else {
                        z3 = true;
                    }
                }
                i++;
            }
            z = true;
            z2 = false;
            long j2 = um0Var.l ^ (-9223372034707292160L);
            if (pm0Var.d == 12) {
                z4 = true;
            } else {
                z4 = false;
            }
            if (hu0Var.i(hu0Var.e(j2)) == 0.0f) {
                z5 = true;
            } else {
                z5 = false;
            }
            if (!z5 || z4) {
                if (!(zbVar.q(new n21(j2, um0Var.b, z4)) instanceof nd) || z3) {
                    z3 = true;
                }
            }
            if (z3 && !this.d) {
                return z2;
            }
            return z;
        }
        z = true;
        z2 = false;
        z3 = z2;
        if (z3) {
        }
        return z;
    }
}
