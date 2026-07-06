package defpackage;

import java.util.Iterator;
import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ql0 extends z {
    public final /* synthetic */ int e;
    public final ol0 f;

    public /* synthetic */ ql0(int i, ol0 ol0Var) {
        this.e = i;
        this.f = ol0Var;
    }

    @Override // defpackage.z
    public final int a() {
        switch (this.e) {
            case 0:
                return this.f.i;
            default:
                return this.f.i;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean add(Object obj) {
        switch (this.e) {
            case 0:
                throw new UnsupportedOperationException();
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        switch (this.e) {
            case 0:
                this.f.clear();
                return;
            default:
                this.f.clear();
                return;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        switch (this.e) {
            case 0:
                if (obj instanceof Map.Entry) {
                    Map.Entry entry = (Map.Entry) obj;
                    Object key = entry.getKey();
                    ol0 ol0Var = this.f;
                    Object obj2 = ol0Var.get(key);
                    if (obj2 != null) {
                        return obj2.equals(entry.getValue());
                    }
                    if (entry.getValue() == null && ol0Var.containsKey(entry.getKey())) {
                        return true;
                    }
                }
                return false;
            default:
                return this.f.containsKey(obj);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        switch (this.e) {
            case 0:
                return new rl0(this.f);
            default:
                b31[] b31VarArr = new b31[8];
                for (int i = 0; i < 8; i++) {
                    b31VarArr[i] = new c31(1);
                }
                return new pl0(this.f, b31VarArr);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        switch (this.e) {
            case 0:
                if (!(obj instanceof Map.Entry)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                return this.f.remove(entry.getKey(), entry.getValue());
            default:
                ol0 ol0Var = this.f;
                if (!ol0Var.containsKey(obj)) {
                    return false;
                }
                ol0Var.remove(obj);
                return true;
        }
    }
}
