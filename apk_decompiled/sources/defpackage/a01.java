package defpackage;

import java.io.Serializable;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class a01 implements q50, Serializable {
    public vu e;
    public volatile Object f;
    public final Object g;

    public a01(vu vuVar) {
        vuVar.getClass();
        this.e = vuVar;
        this.f = dt0.h;
        this.g = this;
    }

    @Override // defpackage.q50
    public final Object getValue() {
        Object obj;
        Object obj2 = this.f;
        dt0 dt0Var = dt0.h;
        if (obj2 != dt0Var) {
            return obj2;
        }
        synchronized (this.g) {
            obj = this.f;
            if (obj == dt0Var) {
                vu vuVar = this.e;
                vuVar.getClass();
                obj = vuVar.a();
                this.f = obj;
                this.e = null;
            }
        }
        return obj;
    }

    public final String toString() {
        if (this.f != dt0.h) {
            return String.valueOf(getValue());
        }
        return "Lazy value not initialized yet.";
    }
}
