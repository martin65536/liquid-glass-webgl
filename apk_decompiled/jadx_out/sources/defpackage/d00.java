package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class d00 {
    public static final d00 e;
    public static final d00 f;
    public static final d00 g;
    public static final /* synthetic */ d00[] h;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.Enum, d00] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.Enum, d00] */
    /* JADX WARN: Type inference failed for: r3v1, types: [java.lang.Enum, d00] */
    static {
        ?? r0 = new Enum("Yes", 0);
        e = r0;
        ?? r1 = new Enum("No", 1);
        f = r1;
        ?? r3 = new Enum("NotInitialized", 2);
        g = r3;
        h = new d00[]{r0, r1, r3};
    }

    public static d00 valueOf(String str) {
        return (d00) Enum.valueOf(d00.class, str);
    }

    public static d00[] values() {
        return (d00[]) h.clone();
    }
}
