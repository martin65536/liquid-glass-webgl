package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class tc implements wj, xj {
    public static final dt0 f = new dt0(18);
    public static final tc g = new tc(1);
    public final /* synthetic */ int e;

    public /* synthetic */ tc(int i) {
        this.e = i;
    }

    @Override // defpackage.wj
    public final xj getKey() {
        switch (this.e) {
            case 0:
                return f;
            default:
                return this;
        }
    }

    @Override // defpackage.yj
    public final yj i(yj yjVar) {
        switch (this.e) {
            case 0:
                return jc0.z(this, yjVar);
            default:
                return jc0.z(this, yjVar);
        }
    }

    @Override // defpackage.yj
    public final wj j(xj xjVar) {
        switch (this.e) {
            case 0:
                return jc0.p(this, xjVar);
            default:
                return jc0.p(this, xjVar);
        }
    }

    @Override // defpackage.yj
    public final Object n(kv kvVar, Object obj) {
        switch (this.e) {
            case 0:
                return kvVar.d(obj, this);
            default:
                return kvVar.d(obj, this);
        }
    }

    @Override // defpackage.yj
    public final yj s(xj xjVar) {
        switch (this.e) {
            case 0:
                return jc0.x(this, xjVar);
            default:
                return jc0.x(this, xjVar);
        }
    }
}
