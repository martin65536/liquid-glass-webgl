package defpackage;

import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class gn0 {
    public final List a;
    public final List[] b;
    public int c;
    public int d;
    public boolean e;
    public final /* synthetic */ hn0 f;

    public gn0(hn0 hn0Var, List list) {
        this.f = hn0Var;
        this.a = list;
        this.b = new List[list.size()];
        if (list.isEmpty()) {
            t00.a("NestedPrefetchController shouldn't be created with no states");
        }
    }
}
