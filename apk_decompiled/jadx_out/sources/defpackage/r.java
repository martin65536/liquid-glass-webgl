package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class r implements wj {
    public final xj e;

    public r(xj xjVar) {
        this.e = xjVar;
    }

    @Override // defpackage.wj
    public final xj getKey() {
        return this.e;
    }

    @Override // defpackage.yj
    public final /* bridge */ yj i(yj yjVar) {
        return jc0.z(this, yjVar);
    }

    @Override // defpackage.yj
    public /* bridge */ wj j(xj xjVar) {
        return jc0.p(this, xjVar);
    }

    @Override // defpackage.yj
    public final Object n(kv kvVar, Object obj) {
        return kvVar.d(obj, this);
    }

    @Override // defpackage.yj
    public /* bridge */ yj s(xj xjVar) {
        return jc0.x(this, xjVar);
    }
}
