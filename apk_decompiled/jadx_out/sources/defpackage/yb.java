package defpackage;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class yb implements y51 {
    public Object e = bc.p;
    public pc f;
    public final /* synthetic */ zb g;

    public yb(zb zbVar) {
        this.g = zbVar;
    }

    @Override // defpackage.y51
    public final void a(ku0 ku0Var, int i) {
        pc pcVar = this.f;
        if (pcVar != null) {
            pcVar.a(ku0Var, i);
        }
    }

    public final Object b(jj jjVar) {
        od odVar;
        Object obj = this.e;
        boolean z = true;
        if (obj == bc.p || obj == bc.l) {
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = zb.k;
            zb zbVar = this.g;
            od odVar2 = (od) atomicReferenceFieldUpdater.get(zbVar);
            while (true) {
                zbVar.getClass();
                if (zbVar.x(zb.f.get(zbVar), true)) {
                    this.e = bc.l;
                    Throwable p = zbVar.p();
                    if (p == null) {
                        z = false;
                    } else {
                        int i = cy0.a;
                        throw p;
                    }
                } else {
                    long andIncrement = zb.g.getAndIncrement(zbVar);
                    long j = bc.b;
                    long j2 = andIncrement / j;
                    int i2 = (int) (andIncrement % j);
                    if (odVar2.e != j2) {
                        odVar = zbVar.m(j2, odVar2);
                        if (odVar == null) {
                            continue;
                        }
                    } else {
                        odVar = odVar2;
                    }
                    Object J = zbVar.J(odVar, i2, andIncrement, null);
                    wq wqVar = bc.m;
                    if (J != wqVar) {
                        wq wqVar2 = bc.o;
                        if (J == wqVar2) {
                            if (andIncrement < zbVar.t()) {
                                odVar.a();
                            }
                            odVar2 = odVar;
                        } else {
                            if (J == bc.n) {
                                zb zbVar2 = this.g;
                                pc w = dl.w(t20.w(jjVar));
                                try {
                                    this.f = w;
                                    Object J2 = zbVar2.J(odVar, i2, andIncrement, this);
                                    if (J2 == wqVar) {
                                        a(odVar, i2);
                                    } else {
                                        if (J2 == wqVar2) {
                                            if (andIncrement < zbVar2.t()) {
                                                odVar.a();
                                            }
                                            od odVar3 = (od) zb.k.get(zbVar2);
                                            while (true) {
                                                if (zbVar2.x(zb.f.get(zbVar2), true)) {
                                                    pc pcVar = this.f;
                                                    pcVar.getClass();
                                                    this.f = null;
                                                    this.e = bc.l;
                                                    Throwable p2 = zbVar.p();
                                                    if (p2 == null) {
                                                        pcVar.u(Boolean.FALSE);
                                                    } else {
                                                        pcVar.u(new jq0(p2));
                                                    }
                                                } else {
                                                    long andIncrement2 = zb.g.getAndIncrement(zbVar2);
                                                    long j3 = bc.b;
                                                    long j4 = andIncrement2 / j3;
                                                    int i3 = (int) (andIncrement2 % j3);
                                                    if (odVar3.e != j4) {
                                                        od m = zbVar2.m(j4, odVar3);
                                                        if (m != null) {
                                                            odVar3 = m;
                                                        }
                                                    }
                                                    Object J3 = zbVar2.J(odVar3, i3, andIncrement2, this);
                                                    if (J3 == bc.m) {
                                                        a(odVar3, i3);
                                                        break;
                                                    }
                                                    if (J3 == bc.o) {
                                                        if (andIncrement2 < zbVar2.t()) {
                                                            odVar3.a();
                                                        }
                                                    } else if (J3 != bc.n) {
                                                        odVar3.a();
                                                        this.e = J3;
                                                        this.f = null;
                                                    } else {
                                                        throw new IllegalStateException("unexpected");
                                                    }
                                                }
                                            }
                                        } else {
                                            odVar.a();
                                            this.e = J2;
                                            this.f = null;
                                        }
                                        w.F(Boolean.TRUE, null);
                                    }
                                    return w.p();
                                } catch (Throwable th) {
                                    w.D();
                                    throw th;
                                }
                            }
                            odVar.a();
                            this.e = J;
                        }
                    } else {
                        v7.o("unreachable");
                        return null;
                    }
                }
            }
        }
        return Boolean.valueOf(z);
    }

    public final Object c() {
        Object obj = this.e;
        wq wqVar = bc.p;
        if (obj != wqVar) {
            this.e = wqVar;
            if (obj != bc.l) {
                return obj;
            }
            Throwable r = this.g.r();
            int i = cy0.a;
            throw r;
        }
        v7.o("`hasNext()` has not been invoked");
        return null;
    }
}
