package defpackage;

import java.util.ArrayList;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class kt0 implements nj0 {
    public final int e;
    public final List f;
    public Float g = null;
    public Float h = null;
    public et0 i = null;
    public et0 j = null;

    public kt0(int i, ArrayList arrayList) {
        this.e = i;
        this.f = arrayList;
    }

    @Override // defpackage.nj0
    public final boolean H() {
        return this.f.contains(this);
    }
}
