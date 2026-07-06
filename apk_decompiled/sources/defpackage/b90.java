package defpackage;

import java.util.Iterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class b90 implements Iterator, q30 {
    public final String e;
    public int f;
    public int g;
    public int h;
    public int i;

    public b90(String str) {
        this.e = str;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        int i;
        int i2 = this.f;
        if (i2 != 0) {
            if (i2 != 1) {
                return false;
            }
            return true;
        }
        int i3 = 2;
        if (this.i < 0) {
            this.f = 2;
            return false;
        }
        String str = this.e;
        int length = str.length();
        int length2 = str.length();
        for (int i4 = this.g; i4 < length2; i4++) {
            char charAt = str.charAt(i4);
            if (charAt == '\n' || charAt == '\r') {
                if (charAt != '\r' || (i = i4 + 1) >= str.length() || str.charAt(i) != '\n') {
                    i3 = 1;
                }
                length = i4;
                this.f = 1;
                this.i = i3;
                this.h = length;
                return true;
            }
        }
        i3 = -1;
        this.f = 1;
        this.i = i3;
        this.h = length;
        return true;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (hasNext()) {
            this.f = 0;
            int i = this.h;
            int i2 = this.g;
            this.g = this.i + i;
            return this.e.subSequence(i2, i).toString();
        }
        v7.n();
        return null;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
