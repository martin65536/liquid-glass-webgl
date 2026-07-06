package defpackage;

import java.io.Serializable;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class jf implements yj, Serializable {
    public final yj e;
    public final wj f;

    public jf(wj wjVar, yj yjVar) {
        yjVar.getClass();
        wjVar.getClass();
        this.e = yjVar;
        this.f = wjVar;
    }

    public final boolean equals(Object obj) {
        boolean z;
        if (this != obj) {
            if (obj instanceof jf) {
                jf jfVar = (jf) obj;
                int i = 2;
                jf jfVar2 = jfVar;
                int i2 = 2;
                while (true) {
                    yj yjVar = jfVar2.e;
                    if (yjVar instanceof jf) {
                        jfVar2 = (jf) yjVar;
                    } else {
                        jfVar2 = null;
                    }
                    if (jfVar2 == null) {
                        break;
                    }
                    i2++;
                }
                jf jfVar3 = this;
                while (true) {
                    yj yjVar2 = jfVar3.e;
                    if (yjVar2 instanceof jf) {
                        jfVar3 = (jf) yjVar2;
                    } else {
                        jfVar3 = null;
                    }
                    if (jfVar3 == null) {
                        break;
                    }
                    i++;
                }
                if (i2 == i) {
                    while (true) {
                        wj wjVar = this.f;
                        if (!o20.e(jfVar.j(wjVar.getKey()), wjVar)) {
                            z = false;
                            break;
                        }
                        yj yjVar3 = this.e;
                        if (yjVar3 instanceof jf) {
                            this = (jf) yjVar3;
                        } else {
                            yjVar3.getClass();
                            wj wjVar2 = (wj) yjVar3;
                            z = o20.e(jfVar.j(wjVar2.getKey()), wjVar2);
                            break;
                        }
                    }
                    if (z) {
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return this.f.hashCode() + this.e.hashCode();
    }

    @Override // defpackage.yj
    public final yj i(yj yjVar) {
        yjVar.getClass();
        if (yjVar == cr.e) {
            return this;
        }
        return (yj) yjVar.n(new w4(3, (byte) 0), this);
    }

    @Override // defpackage.yj
    public final wj j(xj xjVar) {
        xjVar.getClass();
        while (true) {
            wj j = this.f.j(xjVar);
            if (j != null) {
                return j;
            }
            yj yjVar = this.e;
            if (yjVar instanceof jf) {
                this = (jf) yjVar;
            } else {
                return yjVar.j(xjVar);
            }
        }
    }

    @Override // defpackage.yj
    public final Object n(kv kvVar, Object obj) {
        return kvVar.d(this.e.n(kvVar, obj), this.f);
    }

    @Override // defpackage.yj
    public final yj s(xj xjVar) {
        xjVar.getClass();
        wj wjVar = this.f;
        wj j = wjVar.j(xjVar);
        yj yjVar = this.e;
        if (j != null) {
            return yjVar;
        }
        yj s = yjVar.s(xjVar);
        if (s == yjVar) {
            return this;
        }
        if (s == cr.e) {
            return wjVar;
        }
        return new jf(wjVar, s);
    }

    public final String toString() {
        return "[" + ((String) n(new w4(1, (byte) 0), "")) + ']';
    }
}
