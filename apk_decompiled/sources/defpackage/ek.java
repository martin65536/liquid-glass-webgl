package defpackage;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ek extends Thread {
    public static final /* synthetic */ AtomicIntegerFieldUpdater m = AtomicIntegerFieldUpdater.newUpdater(ek.class, "workerCtl$volatile");
    public final d81 e;
    public final ep0 f;
    public fk g;
    public long h;
    public long i;
    private volatile int indexInArray;
    public int j;
    public boolean k;
    public final /* synthetic */ gk l;
    private volatile Object nextParkedWorker;
    private volatile /* synthetic */ int workerCtl$volatile;

    /* JADX WARN: Type inference failed for: r3v5, types: [ep0, java.lang.Object] */
    public ek(gk gkVar, int i) {
        this.l = gkVar;
        setDaemon(true);
        setContextClassLoader(gk.class.getClassLoader());
        this.e = new d81();
        this.f = new Object();
        this.g = fk.h;
        this.nextParkedWorker = gk.o;
        int nanoTime = (int) System.nanoTime();
        this.j = nanoTime == 0 ? 42 : nanoTime;
        f(i);
    }

    public final q01 a(boolean z) {
        q01 e;
        q01 e2;
        long j;
        fk fkVar = this.g;
        gk gkVar = this.l;
        boolean z2 = true;
        d81 d81Var = this.e;
        fk fkVar2 = fk.e;
        if (fkVar != fkVar2) {
            AtomicLongFieldUpdater atomicLongFieldUpdater = gk.m;
            do {
                j = atomicLongFieldUpdater.get(gkVar);
                if (((int) ((9223367638808264704L & j) >> 42)) == 0) {
                    q01 d = d81Var.d();
                    if (d == null && (d = (q01) gkVar.j.d()) == null) {
                        return i(1);
                    }
                    return d;
                }
            } while (!gk.m.compareAndSet(gkVar, j, j - 4398046511104L));
            this.g = fkVar2;
        }
        if (z) {
            if (d(gkVar.e * 2) != 0) {
                z2 = false;
            }
            if (z2 && (e2 = e()) != null) {
                return e2;
            }
            d81Var.getClass();
            d81.b.getClass();
            q01 q01Var = (q01) qc.a(qc.a, d81Var, d81.f, null);
            if (q01Var == null) {
                q01Var = d81Var.c();
            }
            if (q01Var != null) {
                return q01Var;
            }
            if (!z2 && (e = e()) != null) {
                return e;
            }
        } else {
            q01 e3 = e();
            if (e3 != null) {
                return e3;
            }
        }
        return i(3);
    }

    public final int b() {
        return this.indexInArray;
    }

    public final Object c() {
        return this.nextParkedWorker;
    }

    public final int d(int i) {
        int i2 = this.j;
        int i3 = i2 ^ (i2 << 13);
        int i4 = i3 ^ (i3 >> 17);
        int i5 = i4 ^ (i4 << 5);
        this.j = i5;
        int i6 = i - 1;
        if ((i6 & i) == 0) {
            return i6 & i5;
        }
        return (Integer.MAX_VALUE & i5) % i;
    }

    public final q01 e() {
        int d = d(2);
        gk gkVar = this.l;
        xw xwVar = gkVar.j;
        xw xwVar2 = gkVar.i;
        if (d == 0) {
            q01 q01Var = (q01) xwVar2.d();
            if (q01Var != null) {
                return q01Var;
            }
            return (q01) xwVar.d();
        }
        q01 q01Var2 = (q01) xwVar.d();
        if (q01Var2 != null) {
            return q01Var2;
        }
        return (q01) xwVar2.d();
    }

    public final void f(int i) {
        String valueOf;
        StringBuilder sb = new StringBuilder();
        sb.append(this.l.h);
        sb.append("-worker-");
        if (i == 0) {
            valueOf = "TERMINATED";
        } else {
            valueOf = String.valueOf(i);
        }
        sb.append(valueOf);
        setName(sb.toString());
        this.indexInArray = i;
    }

    public final void g(Object obj) {
        this.nextParkedWorker = obj;
    }

    public final boolean h(fk fkVar) {
        boolean z;
        fk fkVar2 = this.g;
        if (fkVar2 == fk.e) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            gk.m.addAndGet(this.l, 4398046511104L);
        }
        if (fkVar2 != fkVar) {
            this.g = fkVar;
        }
        return z;
    }

    public final q01 i(int i) {
        q01 q01Var;
        long f;
        AtomicLongFieldUpdater atomicLongFieldUpdater = gk.m;
        gk gkVar = this.l;
        int i2 = (int) (atomicLongFieldUpdater.get(gkVar) & 2097151);
        if (i2 < 2) {
            return null;
        }
        int d = d(i2);
        long j = Long.MAX_VALUE;
        for (int i3 = 0; i3 < i2; i3++) {
            boolean z = true;
            d++;
            if (d > i2) {
                d = 1;
            }
            ek ekVar = (ek) gkVar.k.b(d);
            if (ekVar != null && ekVar != this) {
                d81 d81Var = ekVar.e;
                if (i == 3) {
                    q01Var = d81Var.c();
                } else {
                    d81Var.getClass();
                    int i4 = d81.d.get(d81Var);
                    int i5 = d81.c.get(d81Var);
                    if (i != 1) {
                        z = false;
                    }
                    while (i4 != i5 && (!z || d81.e.get(d81Var) != 0)) {
                        int i6 = i4 + 1;
                        q01Var = d81Var.e(i4, z);
                        if (q01Var != null) {
                            break;
                        }
                        i4 = i6;
                    }
                    q01Var = null;
                }
                ep0 ep0Var = this.f;
                if (q01Var != null) {
                    ep0Var.e = q01Var;
                    f = -1;
                } else {
                    f = d81Var.f(i, ep0Var);
                }
                if (f == -1) {
                    q01 q01Var2 = (q01) ep0Var.e;
                    ep0Var.e = null;
                    return q01Var2;
                }
                if (f > 0) {
                    j = Math.min(j, f);
                }
            }
        }
        if (j == Long.MAX_VALUE) {
            j = 0;
        }
        this.i = j;
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:80:0x0004, code lost:
    
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0004, code lost:
    
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0004, code lost:
    
        continue;
     */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void run() {
        /*
            Method dump skipped, instructions count: 417
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ek.run():void");
    }
}
