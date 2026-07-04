package defpackage;

import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class g8 extends jw0 implements Map {
    public b8 h;
    public d8 i;
    public f8 j;

    @Override // java.util.Map
    public final Set entrySet() {
        b8 b8Var = this.h;
        if (b8Var == null) {
            b8 b8Var2 = new b8(this);
            this.h = b8Var2;
            return b8Var2;
        }
        return b8Var;
    }

    public final boolean i(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (!super.containsKey(it.next())) {
                return false;
            }
        }
        return true;
    }

    public final boolean j(Collection collection) {
        int i = this.g;
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            super.remove(it.next());
        }
        if (i != this.g) {
            return true;
        }
        return false;
    }

    @Override // java.util.Map
    public final Set keySet() {
        d8 d8Var = this.i;
        if (d8Var == null) {
            d8 d8Var2 = new d8(this);
            this.i = d8Var2;
            return d8Var2;
        }
        return d8Var;
    }

    @Override // java.util.Map
    public final void putAll(Map map) {
        int size = map.size() + this.g;
        int i = this.g;
        int[] iArr = this.e;
        if (iArr.length < size) {
            this.e = Arrays.copyOf(iArr, size);
            this.f = Arrays.copyOf(this.f, size * 2);
        }
        if (this.g == i) {
            for (Map.Entry entry : map.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
            return;
        }
        throw new ConcurrentModificationException();
    }

    @Override // java.util.Map
    public final Collection values() {
        f8 f8Var = this.j;
        if (f8Var == null) {
            f8 f8Var2 = new f8(this);
            this.j = f8Var2;
            return f8Var2;
        }
        return f8Var;
    }
}
