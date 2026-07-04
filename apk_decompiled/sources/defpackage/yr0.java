package defpackage;

import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class yr0 implements Map.Entry {
    public final Object e;
    public final k80 f;
    public yr0 g;
    public yr0 h;

    public yr0(i80 i80Var, k80 k80Var) {
        this.e = i80Var;
        this.f = k80Var;
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof yr0) {
            yr0 yr0Var = (yr0) obj;
            if (this.e.equals(yr0Var.e) && this.f == yr0Var.f) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return this.e;
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return this.f;
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        return this.f.hashCode() ^ this.e.hashCode();
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        throw new UnsupportedOperationException("An entry modification is not supported");
    }

    public final String toString() {
        return this.e + "=" + this.f;
    }
}
