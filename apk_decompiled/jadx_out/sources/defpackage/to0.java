package defpackage;

import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class to0 extends th {
    public final q6 a;
    public final r7 b;
    public final Object c;
    public d30 d;
    public Throwable e;
    public final ArrayList f;
    public List g;
    public we0 h;
    public final ef0 i;
    public final ArrayList j;
    public final ArrayList k;
    public final ve0 l;
    public final c4 m;
    public final ve0 n;
    public final ve0 o;
    public ArrayList p;
    public we0 q;
    public pc r;
    public final ky0 s;
    public boolean t;
    public final ky0 u;
    public final r7 v;
    public final f30 w;
    public final yj x;
    public final rt y;
    public static final ky0 z = o20.c(wl0.h);
    public static final AtomicReference A = new AtomicReference(Boolean.FALSE);

    public to0(yj yjVar) {
        q6 q6Var = new q6(new no0(this, 0));
        this.a = q6Var;
        this.b = new r7(new no0(this, 1));
        this.c = new Object();
        this.f = new ArrayList();
        this.h = new we0();
        this.i = new ef0(new yh[16]);
        this.j = new ArrayList();
        this.k = new ArrayList();
        this.l = new ve0();
        this.m = new c4(12);
        this.n = new ve0();
        this.o = new ve0();
        this.s = o20.c(null);
        this.u = o20.c(po0.g);
        this.v = new r7(11);
        f30 f30Var = new f30((d30) yjVar.j(x1.L));
        f30Var.p(new l(12, this));
        this.w = f30Var;
        this.x = yjVar.i(q6Var).i(f30Var);
        this.y = new rt(25);
    }

    public static final void A(to0 to0Var, d30 d30Var) {
        synchronized (to0Var.c) {
            Throwable th = to0Var.e;
            if (th == null) {
                if (((po0) to0Var.u.getValue()).compareTo(po0.f) > 0) {
                    if (to0Var.d == null) {
                        to0Var.d = d30Var;
                        if (to0Var.D() != null) {
                            rh.a("called outside of runRecomposeAndApplyChanges");
                        }
                    } else {
                        throw new IllegalStateException("Recomposer already running");
                    }
                } else {
                    throw new IllegalStateException("Recomposer shut down");
                }
            } else {
                throw th;
            }
        }
    }

    public static void B(ze0 ze0Var) {
        try {
            if (!(ze0Var.w() instanceof xw0)) {
            } else {
                throw new IllegalStateException("Unsupported concurrent change during composition. A state object was modified by composition as well as being modified outside composition.");
            }
        } finally {
            ze0Var.c();
        }
    }

    public static final void M(ArrayList arrayList, to0 to0Var, yh yhVar) {
        arrayList.clear();
        synchronized (to0Var.c) {
            Iterator it = to0Var.k.iterator();
            if (it.hasNext()) {
                ((wd0) it.next()).getClass();
                throw null;
            }
        }
    }

    public static final Object w(to0 to0Var, so0 so0Var) {
        pc pcVar;
        if (!to0Var.H()) {
            pc pcVar2 = new pc(1, t20.w(so0Var));
            pcVar2.s();
            synchronized (to0Var.c) {
                if (to0Var.H()) {
                    pcVar = pcVar2;
                } else {
                    to0Var.r = pcVar2;
                    pcVar = null;
                }
            }
            if (pcVar != null) {
                pcVar.u(x31.a);
            }
            Object p = pcVar2.p();
            if (p == ik.e) {
                return p;
            }
            return x31.a;
        }
        return x31.a;
    }

    public static final void x(to0 to0Var) {
        int i;
        pe0 pe0Var;
        synchronized (to0Var.c) {
            try {
                if (to0Var.l.j()) {
                    pe0 b = de0.b(to0Var.l);
                    to0Var.l.a();
                    c4 c4Var = to0Var.m;
                    ((ve0) c4Var.f).a();
                    ((ve0) c4Var.g).a();
                    to0Var.o.a();
                    pe0Var = new pe0(b.b);
                    Object[] objArr = b.a;
                    int i2 = b.b;
                    for (int i3 = 0; i3 < i2; i3++) {
                        wd0 wd0Var = (wd0) objArr[i3];
                        pe0Var.a(new xj0(wd0Var, to0Var.n.g(wd0Var)));
                    }
                    to0Var.n.a();
                } else {
                    pe0Var = yg0.b;
                    pe0Var.getClass();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        Object[] objArr2 = pe0Var.a;
        int i4 = pe0Var.b;
        for (i = 0; i < i4; i++) {
            xj0 xj0Var = (xj0) objArr2[i];
        }
    }

    public static final boolean y(to0 to0Var) {
        boolean E;
        synchronized (to0Var.c) {
            E = to0Var.E();
        }
        return E;
    }

    public static final List z(to0 to0Var) {
        List I;
        synchronized (to0Var.c) {
            I = to0Var.I();
        }
        return I;
    }

    public final void C() {
        synchronized (this.c) {
            if (((po0) this.u.getValue()).compareTo(po0.i) >= 0) {
                ky0 ky0Var = this.u;
                po0 po0Var = po0.f;
                ky0Var.getClass();
                ky0Var.j(null, po0Var);
            }
        }
        this.w.a(null);
    }

    public final nc D() {
        ky0 ky0Var = this.u;
        int compareTo = ((po0) ky0Var.getValue()).compareTo(po0.f);
        ky0 ky0Var2 = this.s;
        ArrayList arrayList = this.k;
        ArrayList arrayList2 = this.j;
        ef0 ef0Var = this.i;
        if (compareTo <= 0) {
            List I = I();
            int size = I.size();
            for (int i = 0; i < size; i++) {
            }
            this.f.clear();
            this.g = er.e;
            this.h = new we0();
            ef0Var.g();
            arrayList2.clear();
            arrayList.clear();
            this.p = null;
            pc pcVar = this.r;
            if (pcVar != null) {
                pcVar.x(null);
            }
            this.r = null;
            ky0Var2.i(null);
            return null;
        }
        Object value = ky0Var2.getValue();
        po0 po0Var = po0.j;
        po0 po0Var2 = po0.g;
        if (value == null) {
            if (this.d == null) {
                this.h = new we0();
                ef0Var.g();
                if (E() || G()) {
                    po0Var2 = po0.h;
                }
            } else {
                po0Var2 = (ef0Var.g != 0 || this.h.h() || !arrayList2.isEmpty() || !arrayList.isEmpty() || E() || G() || this.l.j()) ? po0Var : po0.i;
            }
        }
        ky0Var.j(null, po0Var2);
        if (po0Var2 != po0Var) {
            return null;
        }
        pc pcVar2 = this.r;
        this.r = null;
        return pcVar2;
    }

    public final boolean E() {
        if (!this.t && (((o8) ((a9) this.a.g).c).get() & 134217727) > 0) {
            return true;
        }
        return false;
    }

    public final boolean F() {
        if (this.i.g == 0 && !E() && !G() && !this.l.j()) {
            return false;
        }
        return true;
    }

    public final boolean G() {
        if (!this.t && (((o8) ((a9) this.b.g).c).get() & 134217727) > 0) {
            return true;
        }
        return false;
    }

    public final boolean H() {
        boolean z2;
        synchronized (this.c) {
            if (!this.h.h() && this.i.g == 0 && !E()) {
                if (!G()) {
                    z2 = false;
                }
            }
            z2 = true;
        }
        return z2;
    }

    public final List I() {
        List arrayList;
        List list = this.g;
        if (list != null) {
            return list;
        }
        ArrayList arrayList2 = this.f;
        if (arrayList2.isEmpty()) {
            arrayList = er.e;
        } else {
            arrayList = new ArrayList(arrayList2);
        }
        this.g = arrayList;
        return arrayList;
    }

    public final void J() {
        nc D;
        synchronized (this.c) {
            D = D();
            if (((po0) this.u.getValue()).compareTo(po0.f) <= 0) {
                Throwable th = this.e;
                CancellationException cancellationException = new CancellationException("Recomposer shutdown; frame clock awaiter will never resume");
                cancellationException.initCause(th);
                throw cancellationException;
            }
        }
        if (D != null) {
            ((pc) D).u(x31.a);
        }
    }

    public final void K() {
        synchronized (this.c) {
            this.t = true;
        }
    }

    public final void L(yh yhVar) {
        synchronized (this.c) {
            ArrayList arrayList = this.k;
            if (arrayList.size() > 0) {
                ((wd0) arrayList.get(0)).getClass();
                throw null;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x013a, code lost:
    
        r3 = r11.size();
        r4 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x013f, code lost:
    
        if (r4 >= r3) goto L116;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0149, code lost:
    
        if (((defpackage.xj0) r11.get(r4)).f == null) goto L115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x014b, code lost:
    
        r4 = r4 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x014e, code lost:
    
        r3 = new java.util.ArrayList(r11.size());
        r4 = r11.size();
        r9 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x015c, code lost:
    
        if (r9 >= r4) goto L117;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x015e, code lost:
    
        r12 = (defpackage.xj0) r11.get(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0166, code lost:
    
        if (r12.f != null) goto L118;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0168, code lost:
    
        r12 = (defpackage.wd0) r12.e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x016f, code lost:
    
        r9 = r9 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0172, code lost:
    
        r4 = r18.c;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0174, code lost:
    
        monitor-enter(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0175, code lost:
    
        defpackage.re.P(r18.k, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x017a, code lost:
    
        monitor-exit(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x017b, code lost:
    
        r3 = new java.util.ArrayList(r11.size());
        r4 = r11.size();
        r9 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0189, code lost:
    
        if (r9 >= r4) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x018b, code lost:
    
        r12 = r11.get(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0194, code lost:
    
        if (((defpackage.xj0) r12).f == null) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0196, code lost:
    
        r3.add(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0199, code lost:
    
        r9 = r9 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x019c, code lost:
    
        r11 = r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.util.List N(java.util.List r19, defpackage.we0 r20) {
        /*
            Method dump skipped, instructions count: 457
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.to0.N(java.util.List, we0):java.util.List");
    }

    public final yh O(yh yhVar, we0 we0Var) {
        ze0 ze0Var;
        ze0 D;
        if (yhVar.z.F || yhVar.A == 3) {
            return null;
        }
        we0 we0Var2 = this.q;
        if (we0Var2 == null || !we0Var2.c(yhVar)) {
            l lVar = new l(11, yhVar);
            c cVar = new c(17, yhVar, we0Var);
            ww0 j = cx0.j();
            if (j instanceof ze0) {
                ze0Var = (ze0) j;
            } else {
                ze0Var = null;
            }
            if (ze0Var != null && (D = ze0Var.D(lVar, cVar)) != null) {
                try {
                    ww0 j2 = D.j();
                    if (we0Var != null) {
                        try {
                            if (we0Var.h()) {
                                f9 f9Var = new f9(6, we0Var, yhVar);
                                bw bwVar = yhVar.z;
                                if (bwVar.F) {
                                    rh.a("Preparing a composition while composing is not supported");
                                }
                                bwVar.F = true;
                                try {
                                    f9Var.a();
                                    bwVar.F = false;
                                } catch (Throwable th) {
                                    bwVar.F = false;
                                    throw th;
                                }
                            }
                        } catch (Throwable th2) {
                            ww0.q(j2);
                            throw th2;
                        }
                    }
                    boolean w = yhVar.w();
                    ww0.q(j2);
                    if (w) {
                        return yhVar;
                    }
                } finally {
                    B(D);
                }
            } else {
                v7.o("Cannot create a mutable snapshot of an read-only snapshot");
            }
        }
        return null;
    }

    public final void P(Throwable th, yh yhVar) {
        if (((Boolean) A.get()).booleanValue() && !(th instanceof ah)) {
            synchronized (this.c) {
                try {
                    Log.e("ComposeInternal", "Error was captured in composition while live edit was enabled.", th);
                    this.j.clear();
                    this.i.g();
                    this.h = new we0();
                    this.k.clear();
                    this.l.a();
                    this.n.a();
                    ky0 ky0Var = this.s;
                    oo0 oo0Var = new oo0(th);
                    ky0Var.getClass();
                    ky0Var.j(null, oo0Var);
                    if (yhVar != null) {
                        R(yhVar);
                    }
                    if (D() != null) {
                        rh.a("expected to go to inactive state due to composition error");
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
            return;
        }
        synchronized (this.c) {
            Log.e("ComposeInternal", "Error was captured in composition.", th);
            oo0 oo0Var2 = (oo0) this.s.getValue();
            if (oo0Var2 == null) {
                ky0 ky0Var2 = this.s;
                oo0 oo0Var3 = new oo0(th);
                ky0Var2.getClass();
                ky0Var2.j(null, oo0Var3);
            } else {
                throw oo0Var2.a;
            }
        }
        throw th;
    }

    public final boolean Q() {
        boolean F;
        synchronized (this.c) {
            if (this.h.g()) {
                return F();
            }
            List I = I();
            bt0 bt0Var = new bt0(this.h);
            this.h = new we0();
            try {
                int size = I.size();
                for (int i = 0; i < size; i++) {
                    ((yh) I.get(i)).x(bt0Var);
                    if (((po0) this.u.getValue()).compareTo(po0.f) <= 0) {
                        break;
                    }
                }
                synchronized (this.c) {
                    if (D() == null) {
                        F = F();
                    } else {
                        throw new IllegalStateException("called outside of runRecomposeAndApplyChanges");
                    }
                }
                return F;
            } catch (Throwable th) {
                synchronized (this.c) {
                    we0 we0Var = this.h;
                    we0Var.getClass();
                    Iterator<E> it = bt0Var.iterator();
                    while (it.hasNext()) {
                        we0Var.k(it.next());
                    }
                    throw th;
                }
            }
        }
    }

    public final void R(yh yhVar) {
        ArrayList arrayList = this.p;
        if (arrayList == null) {
            arrayList = new ArrayList();
            this.p = arrayList;
        }
        if (!arrayList.contains(yhVar)) {
            arrayList.add(yhVar);
        }
        if (this.f.remove(yhVar)) {
            this.g = null;
        }
    }

    public final void S() {
        nc ncVar;
        synchronized (this.c) {
            if (this.t) {
                this.t = false;
                ncVar = D();
            } else {
                ncVar = null;
            }
        }
        if (ncVar != null) {
            ((pc) ncVar).u(x31.a);
        }
    }

    @Override // defpackage.th
    public final void a(yh yhVar, kv kvVar) {
        po0 po0Var;
        boolean z2;
        ze0 ze0Var;
        ze0 D;
        boolean z3 = yhVar.z.F;
        synchronized (this.c) {
            po0 po0Var2 = (po0) this.u.getValue();
            po0Var = po0.f;
            z2 = true;
            if (po0Var2.compareTo(po0Var) > 0) {
                z2 = true ^ I().contains(yhVar);
            }
        }
        try {
            l lVar = new l(11, yhVar);
            c cVar = new c(17, yhVar, null);
            ww0 j = cx0.j();
            if (j instanceof ze0) {
                ze0Var = (ze0) j;
            } else {
                ze0Var = null;
            }
            if (ze0Var != null && (D = ze0Var.D(lVar, cVar)) != null) {
                try {
                    ww0 j2 = D.j();
                    try {
                        yhVar.j(kvVar);
                        synchronized (this.c) {
                            if (((po0) this.u.getValue()).compareTo(po0Var) > 0 && !I().contains(yhVar)) {
                                this.f.add(yhVar);
                                this.g = null;
                            }
                        }
                        if (!z3) {
                            cx0.j().m();
                        }
                        try {
                            L(yhVar);
                            try {
                                yhVar.d();
                                yhVar.f();
                                if (!z3) {
                                    cx0.j().m();
                                    return;
                                }
                                return;
                            } catch (Throwable th) {
                                P(th, null);
                                return;
                            }
                        } catch (Throwable th2) {
                            P(th2, yhVar);
                            return;
                        }
                    } finally {
                        ww0.q(j2);
                    }
                } finally {
                    B(D);
                }
            }
            throw new IllegalStateException("Cannot create a mutable snapshot of an read-only snapshot");
        } catch (Throwable th3) {
            if (z2) {
                synchronized (this.c) {
                }
            }
            P(th3, yhVar);
        }
    }

    @Override // defpackage.th
    public final we0 b(yh yhVar, iw0 iw0Var, kv kvVar) {
        r7 r7Var = this.v;
        try {
            iw0 iw0Var2 = yhVar.t;
            yhVar.t = iw0Var;
            try {
                a(yhVar, kvVar);
                we0 we0Var = (we0) r7Var.p();
                if (we0Var == null) {
                    we0Var = at0.a;
                    we0Var.getClass();
                }
                return we0Var;
            } finally {
                yhVar.t = iw0Var2;
            }
        } finally {
            r7Var.C(null);
        }
    }

    @Override // defpackage.th
    public final boolean d() {
        return ((Boolean) A.get()).booleanValue();
    }

    @Override // defpackage.th
    public final boolean e() {
        return false;
    }

    @Override // defpackage.th
    public final boolean f() {
        return false;
    }

    @Override // defpackage.th
    public final long g() {
        return 1000L;
    }

    @Override // defpackage.th
    public final sh h() {
        return null;
    }

    @Override // defpackage.th
    public final yj j() {
        return this.x;
    }

    @Override // defpackage.th
    public final boolean k() {
        return false;
    }

    @Override // defpackage.th
    public final void l(yh yhVar) {
        nc ncVar;
        synchronized (this.c) {
            if (!this.i.h(yhVar)) {
                this.i.b(yhVar);
                ncVar = D();
            } else {
                ncVar = null;
            }
        }
        if (ncVar != null) {
            ((pc) ncVar).u(x31.a);
        }
    }

    @Override // defpackage.th
    public final vd0 m(wd0 wd0Var) {
        vd0 vd0Var;
        synchronized (this.c) {
            vd0Var = (vd0) this.n.k(wd0Var);
        }
        return vd0Var;
    }

    @Override // defpackage.th
    public final we0 n(yh yhVar, iw0 iw0Var, we0 we0Var) {
        r7 r7Var = this.v;
        try {
            Q();
            yhVar.x(new bt0(we0Var));
            iw0 iw0Var2 = yhVar.t;
            yhVar.t = iw0Var;
            try {
                yh O = O(yhVar, null);
                if (O != null) {
                    L(yhVar);
                    O.d();
                    O.f();
                }
                we0 we0Var2 = (we0) r7Var.p();
                if (we0Var2 == null) {
                    we0Var2 = at0.a;
                    we0Var2.getClass();
                }
                return we0Var2;
            } finally {
                yhVar.t = iw0Var2;
            }
        } finally {
            r7Var.C(null);
        }
    }

    @Override // defpackage.th
    public final void q(mo0 mo0Var) {
        r7 r7Var = this.v;
        we0 we0Var = (we0) r7Var.p();
        if (we0Var == null) {
            we0 we0Var2 = at0.a;
            we0Var = new we0();
            r7Var.C(we0Var);
        }
        we0Var.a(mo0Var);
    }

    @Override // defpackage.th
    public final void r(yh yhVar) {
        synchronized (this.c) {
            try {
                we0 we0Var = this.q;
                if (we0Var == null) {
                    we0 we0Var2 = at0.a;
                    we0Var = new we0();
                    this.q = we0Var;
                }
                we0Var.a(yhVar);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [hg0, java.lang.Object, z8] */
    @Override // defpackage.th
    public final rc s(n9 n9Var) {
        r7 r7Var = this.b;
        a9 a9Var = (a9) r7Var.g;
        ?? obj = new Object();
        obj.a = n9Var;
        return a9Var.a(obj, (f9) r7Var.h);
    }

    @Override // defpackage.th
    public final void v(yh yhVar) {
        synchronized (this.c) {
            if (this.f.remove(yhVar)) {
                this.g = null;
            }
            this.i.j(yhVar);
            this.j.remove(yhVar);
        }
    }

    @Override // defpackage.th
    public final void o(Set set) {
    }
}
