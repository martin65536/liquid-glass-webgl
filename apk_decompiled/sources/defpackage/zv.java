package defpackage;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class zv extends th {
    public final long a;
    public final boolean b;
    public final boolean c;
    public HashSet d;
    public final we0 e;
    public final ik0 f;
    public final /* synthetic */ bw g;

    public zv(bw bwVar, long j, boolean z, boolean z2, j2 j2Var) {
        this.g = bwVar;
        this.a = j;
        this.b = z;
        this.c = z2;
        we0 we0Var = at0.a;
        this.e = new we0();
        this.f = new ik0(ll0.h, x1.V);
    }

    @Override // defpackage.th
    public final void a(yh yhVar, kv kvVar) {
        this.g.b.a(yhVar, kvVar);
    }

    @Override // defpackage.th
    public final we0 b(yh yhVar, iw0 iw0Var, kv kvVar) {
        return this.g.b.b(yhVar, iw0Var, kvVar);
    }

    @Override // defpackage.th
    public final void c() {
        bw bwVar = this.g;
        bwVar.A--;
    }

    @Override // defpackage.th
    public final boolean d() {
        return this.g.b.d();
    }

    @Override // defpackage.th
    public final boolean e() {
        return this.b;
    }

    @Override // defpackage.th
    public final boolean f() {
        return this.c;
    }

    @Override // defpackage.th
    public final long g() {
        return this.a;
    }

    @Override // defpackage.th
    public final sh h() {
        return this.g.h;
    }

    @Override // defpackage.th
    public final ll0 i() {
        return (ll0) this.f.getValue();
    }

    @Override // defpackage.th
    public final yj j() {
        return this.g.b.j();
    }

    @Override // defpackage.th
    public final boolean k() {
        return this.g.b.k();
    }

    @Override // defpackage.th
    public final void l(yh yhVar) {
        bw bwVar = this.g;
        bwVar.b.l(bwVar.h);
        bwVar.b.l(yhVar);
    }

    @Override // defpackage.th
    public final vd0 m(wd0 wd0Var) {
        return this.g.b.m(wd0Var);
    }

    @Override // defpackage.th
    public final we0 n(yh yhVar, iw0 iw0Var, we0 we0Var) {
        return this.g.b.n(yhVar, iw0Var, we0Var);
    }

    @Override // defpackage.th
    public final void o(Set set) {
        HashSet hashSet = this.d;
        if (hashSet == null) {
            hashSet = new HashSet();
            this.d = hashSet;
        }
        hashSet.add(set);
    }

    @Override // defpackage.th
    public final void p(bw bwVar) {
        this.e.a(bwVar);
    }

    @Override // defpackage.th
    public final void q(mo0 mo0Var) {
        this.g.b.q(mo0Var);
    }

    @Override // defpackage.th
    public final void r(yh yhVar) {
        this.g.b.r(yhVar);
    }

    @Override // defpackage.th
    public final rc s(n9 n9Var) {
        return this.g.b.s(n9Var);
    }

    @Override // defpackage.th
    public final void t() {
        this.g.A++;
    }

    @Override // defpackage.th
    public final void u(bw bwVar) {
        HashSet hashSet = this.d;
        if (hashSet != null) {
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                Set set = (Set) it.next();
                bwVar.getClass();
                set.remove(bwVar.w());
            }
        }
        if (d3.A(bwVar)) {
            this.e.l(bwVar);
        }
    }

    @Override // defpackage.th
    public final void v(yh yhVar) {
        this.g.b.v(yhVar);
    }

    public final void w() {
        we0 we0Var = this.e;
        if (we0Var.h()) {
            HashSet hashSet = this.d;
            if (hashSet != null) {
                Object[] objArr = we0Var.b;
                long[] jArr = we0Var.a;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i = 0;
                    while (true) {
                        long j = jArr[i];
                        if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i2 = 8 - ((~(i - length)) >>> 31);
                            for (int i3 = 0; i3 < i2; i3++) {
                                if ((255 & j) < 128) {
                                    bw bwVar = (bw) objArr[(i << 3) + i3];
                                    Iterator it = hashSet.iterator();
                                    while (it.hasNext()) {
                                        ((Set) it.next()).remove(bwVar.w());
                                    }
                                }
                                j >>= 8;
                            }
                            if (i2 != 8) {
                                break;
                            }
                        }
                        if (i == length) {
                            break;
                        } else {
                            i++;
                        }
                    }
                }
            }
            we0Var.b();
        }
    }
}
