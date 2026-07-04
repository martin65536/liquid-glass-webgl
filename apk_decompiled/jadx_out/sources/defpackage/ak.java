package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class ak extends r implements wj {
    public static final zj f = new zj(x1.A, new pb(1));

    public ak() {
        super(x1.A);
    }

    public abstract void g(yj yjVar, Runnable runnable);

    @Override // defpackage.r, defpackage.yj
    public final wj j(xj xjVar) {
        wj wjVar;
        xjVar.getClass();
        if (xjVar instanceof zj) {
            zj zjVar = (zj) xjVar;
            xj xjVar2 = this.e;
            if ((xjVar2 != zjVar && zjVar.f != xjVar2) || (wjVar = (wj) zjVar.e.e(this)) == null) {
                return null;
            }
            return wjVar;
        }
        if (x1.A != xjVar) {
            return null;
        }
        return this;
    }

    public boolean k(yj yjVar) {
        return !(this instanceof u31);
    }

    public ak o(int i) {
        t20.l(i);
        return new r80(this, i);
    }

    @Override // defpackage.r, defpackage.yj
    public final yj s(xj xjVar) {
        xjVar.getClass();
        if (xjVar instanceof zj) {
            zj zjVar = (zj) xjVar;
            xj xjVar2 = this.e;
            if (xjVar2 != zjVar && zjVar.f != xjVar2) {
                return this;
            }
            if (((wj) zjVar.e.e(this)) == null) {
                return this;
            }
        } else if (x1.A != xjVar) {
            return this;
        }
        return cr.e;
    }

    public String toString() {
        return getClass().getSimpleName() + '@' + dl.v(this);
    }
}
