package defpackage;

import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class uf0 {
    public final List a;
    public final int b;

    public uf0(int i, List list) {
        this.a = list;
        this.b = i;
        if (!list.isEmpty() || i != -1) {
            if (!list.isEmpty()) {
                int size = list.size();
                if (i >= 0 && i < size) {
                    return;
                }
            }
            throw new IllegalArgumentException(("Invalid 'NavigationEventHistory' state:  'currentIndex' must be within the bounds of 'mergedHistory' (or -1 if empty). Received: currentIndex = '" + i + "', bounds = '" + new w10(0, list.size() - 1, 1) + "'.").toString());
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || uf0.class != obj.getClass()) {
            return false;
        }
        uf0 uf0Var = (uf0) obj;
        if (this.b == uf0Var.b && o20.e(this.a, uf0Var.a)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return this.a.hashCode() + (this.b * 31);
    }

    public final String toString() {
        return "NavigationEventHistory(currentIndex=" + this.b + ", mergedHistory=" + this.a + ')';
    }

    public uf0() {
        this(-1, er.e);
    }
}
