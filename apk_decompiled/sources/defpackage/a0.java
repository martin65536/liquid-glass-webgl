package defpackage;

import java.util.Collection;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class a0 implements gv {
    public final /* synthetic */ int e;
    public final /* synthetic */ Collection f;

    public /* synthetic */ a0(int i, Collection collection) {
        this.e = i;
        this.f = collection;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        boolean contains;
        int i = this.e;
        Collection<?> collection = this.f;
        switch (i) {
            case 0:
                contains = collection.contains(obj);
                break;
            case 1:
                contains = collection.contains(obj);
                break;
            default:
                contains = ((List) obj).retainAll(collection);
                break;
        }
        return Boolean.valueOf(contains);
    }
}
