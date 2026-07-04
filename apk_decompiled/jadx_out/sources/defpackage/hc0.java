package defpackage;

import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public class hc0 implements Map.Entry, q30 {
    public final /* synthetic */ int e;
    public final Object f;
    public final Object g;

    public /* synthetic */ hc0(int i, Object obj, Object obj2) {
        this.e = i;
        this.f = obj;
        this.g = obj2;
    }

    @Override // java.util.Map.Entry
    public boolean equals(Object obj) {
        Map.Entry entry;
        switch (this.e) {
            case 0:
                if (obj instanceof Map.Entry) {
                    entry = (Map.Entry) obj;
                } else {
                    entry = null;
                }
                if (entry != null && o20.e(entry.getKey(), this.f) && o20.e(entry.getValue(), getValue())) {
                    return true;
                }
                return false;
            default:
                return super.equals(obj);
        }
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        switch (this.e) {
            case 0:
                return this.f;
            default:
                return this.f;
        }
    }

    @Override // java.util.Map.Entry
    public Object getValue() {
        switch (this.e) {
            case 0:
                return this.g;
            default:
                return this.g;
        }
    }

    @Override // java.util.Map.Entry
    public int hashCode() {
        int i;
        switch (this.e) {
            case 0:
                int i2 = 0;
                Object obj = this.f;
                if (obj != null) {
                    i = obj.hashCode();
                } else {
                    i = 0;
                }
                Object value = getValue();
                if (value != null) {
                    i2 = value.hashCode();
                }
                return i ^ i2;
            default:
                return super.hashCode();
        }
    }

    @Override // java.util.Map.Entry
    public Object setValue(Object obj) {
        switch (this.e) {
            case 0:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
            default:
                throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }
    }

    public String toString() {
        switch (this.e) {
            case 0:
                StringBuilder sb = new StringBuilder();
                sb.append(this.f);
                sb.append('=');
                sb.append(getValue());
                return sb.toString();
            default:
                return super.toString();
        }
    }
}
