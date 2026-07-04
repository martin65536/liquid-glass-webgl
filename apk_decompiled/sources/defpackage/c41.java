package defpackage;

import java.io.Serializable;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class c41 implements q50, Serializable {
    public vu e;
    public Object f;

    @Override // defpackage.q50
    public final Object getValue() {
        if (this.f == dt0.h) {
            vu vuVar = this.e;
            vuVar.getClass();
            this.f = vuVar.a();
            this.e = null;
        }
        return this.f;
    }

    public final String toString() {
        if (this.f != dt0.h) {
            return String.valueOf(getValue());
        }
        return "Lazy value not initialized yet.";
    }
}
