package defpackage;

import java.util.concurrent.atomic.AtomicReferenceArray;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class iv0 extends ku0 {
    public final /* synthetic */ AtomicReferenceArray g;

    public iv0(long j, iv0 iv0Var, int i) {
        super(j, iv0Var, i);
        this.g = new AtomicReferenceArray(hv0.f);
    }

    @Override // defpackage.ku0
    public final int k() {
        return hv0.f;
    }

    @Override // defpackage.ku0
    public final void l(int i, yj yjVar) {
        this.g.set(i, hv0.e);
        m();
    }

    public final String toString() {
        return "SemaphoreSegment[id=" + this.e + ", hashCode=" + hashCode() + ']';
    }
}
