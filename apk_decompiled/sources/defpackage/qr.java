package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class qr implements Runnable, Comparable, un {
    private volatile Object _heap;
    public long e;
    public int f = -1;

    public qr(long j) {
        this.e = j;
    }

    @Override // defpackage.un
    public final void a() {
        rr rrVar;
        synchronized (this) {
            try {
                Object obj = this._heap;
                wq wqVar = o20.e;
                if (obj == wqVar) {
                    return;
                }
                if (obj instanceof rr) {
                    rrVar = (rr) obj;
                } else {
                    rrVar = null;
                }
                if (rrVar != null) {
                    rrVar.b(this);
                }
                this._heap = wqVar;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final y11 b() {
        Object obj = this._heap;
        if (obj instanceof y11) {
            return (y11) obj;
        }
        return null;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        long j = this.e - ((qr) obj).e;
        if (j > 0) {
            return 1;
        }
        if (j < 0) {
            return -1;
        }
        return 0;
    }

    public final int d(long j, rr rrVar, sr srVar) {
        qr qrVar;
        boolean z;
        synchronized (this) {
            if (this._heap == o20.e) {
                return 2;
            }
            synchronized (rrVar) {
                try {
                    qr[] qrVarArr = rrVar.a;
                    if (qrVarArr != null) {
                        qrVar = qrVarArr[0];
                    } else {
                        qrVar = null;
                    }
                    if (sr.m.get(srVar) != 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z) {
                        return 1;
                    }
                    if (qrVar == null) {
                        rrVar.c = j;
                    } else {
                        long j2 = qrVar.e;
                        if (j2 - j < 0) {
                            j = j2;
                        }
                        if (j - rrVar.c > 0) {
                            rrVar.c = j;
                        }
                    }
                    long j3 = this.e;
                    long j4 = rrVar.c;
                    if (j3 - j4 < 0) {
                        this.e = j4;
                    }
                    rrVar.a(this);
                    return 0;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void e(rr rrVar) {
        if (this._heap != o20.e) {
            this._heap = rrVar;
        } else {
            v7.m("Failed requirement.");
        }
    }

    public String toString() {
        return "Delayed[nanos=" + this.e + ']';
    }
}
