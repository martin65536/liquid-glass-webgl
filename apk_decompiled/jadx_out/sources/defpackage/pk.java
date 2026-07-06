package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class pk {
    public static final pk e;
    public static final pk f;
    public static final pk g;
    public static final /* synthetic */ pk[] h;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Enum, pk] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Enum, pk] */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.Enum, pk] */
    static {
        ?? r0 = new Enum("None", 0);
        e = r0;
        ?? r1 = new Enum("Cancelled", 1);
        f = r1;
        ?? r3 = new Enum("Redirected", 2);
        g = r3;
        h = new pk[]{r0, r1, r3, new Enum("RedirectCancelled", 3)};
    }

    public static pk valueOf(String str) {
        return (pk) Enum.valueOf(pk.class, str);
    }

    public static pk[] values() {
        return (pk[]) h.clone();
    }
}
