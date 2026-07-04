package defpackage;

import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class wh implements zi0, wj {
    public static final dt0 f = new dt0(20);
    public final bw e;

    public wh(bw bwVar) {
        this.e = bwVar;
    }

    @Override // defpackage.zi0
    public final List f(Integer num) {
        return this.e.E();
    }

    @Override // defpackage.wj
    public final xj getKey() {
        return f;
    }

    @Override // defpackage.yj
    public final /* bridge */ yj i(yj yjVar) {
        return jc0.z(this, yjVar);
    }

    @Override // defpackage.yj
    public final /* bridge */ wj j(xj xjVar) {
        return jc0.p(this, xjVar);
    }

    @Override // defpackage.zi0
    public final boolean k() {
        return this.e.C;
    }

    @Override // defpackage.yj
    public final Object n(kv kvVar, Object obj) {
        return kvVar.d(obj, this);
    }

    @Override // defpackage.yj
    public final /* bridge */ yj s(xj xjVar) {
        return jc0.x(this, xjVar);
    }
}
